package com.listener.init;

import com.google.common.collect.Maps;
import com.listener.KafkaRebalanceListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/11/15
 */
@Component
@Slf4j
public class KafkaPollTask implements Runnable {

    @Autowired
    private KafkaProperties KafkaProperties;

    /**
     * 这里的消费者应该全局创建好
     * 总消费者数目 = 主题数 * 消费者数目
     * 在有线程池注入该线程
     * z
     */
    private KafkaConsumer kafkaConsumer;
    /**
     * kafka分区是否发生重均衡的标志
     */
    private final AtomicBoolean rebalanceFlag = new AtomicBoolean(false);

    /**
     * 检查是否发生重均衡,如果是,则在跳过此次的拉取,并将标志重置为false
     *
     * @return
     */
    public boolean checkHappenRebance() {
        return rebalanceFlag.compareAndSet(true, false);
    }


    private final Map<TopicPartition, OffsetAndMetadata> offsetInfo = Maps.newConcurrentMap();

    @Override
    public void run() {
        // 初始化消费者
        Map<String, Object> configs = KafkaProperties.buildConsumerProperties();
        kafkaConsumer = new KafkaConsumer<>(configs);
        // 给消费者注册 rebalance 监听
        kafkaConsumer.subscribe(Collections.singleton("topic"), new KafkaRebalanceListener(rebalanceFlag));

        try {
            while (true) {
                ConsumerRecords<byte[], byte[]> records = kafkaConsumer.poll(Duration.ofMillis(60 * 1000));
                records.partitions().forEach(part ->{
                    if (checkHappenRebance()) {
                        // 分区 正在rebalance，中断拉取，防止数据重复消费
                        log.warn("partition rebalance; it will be break,part:{}", part);
                    } else {
                        AtomicInteger count = new AtomicInteger();
                        records.records(part).forEach(record -> {
                            count.getAndIncrement();
                            // 记录当前的偏移量
                            offsetInfo.put(new TopicPartition(record.topic(), record.partition()),
                                    new OffsetAndMetadata(record.offset() + 1, UUID.randomUUID().toString()));
                            // todo 具体业务处理
                            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                            // 处理成功后提交偏移量,分布式下异步提交失败会重试，导致位移回滚提交。
                            // 解决方案：每次发起异步提交时增加此序号，并且将此时的序号作为参数传给回调方法；当消息提交失败回调时，
                            // 检查参数中的序号值与全局的序号值，如果相等那么可以进行重试提交，否则放弃（因为已经有更新的位移提交了）
//                            正常情况下，偶然的提交失败并不是什么大问题，因为后续的提交成功就可以了，所以这个回调可以为null
                            if (count.get() % 1000 == 0) {
                                // 批次提交
                                kafkaConsumer.commitAsync(offsetInfo, (offsets, e) -> {
                                    if (e != null) {
                                        log.error("Commit failed for offsets {}", offsets, e);
                                    }
                                });
                                count.compareAndSet(1000, 0);
                            }
                            offsetInfo.clear();
                        });
                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 我们使用异步提交来提高性能，但最后使用同步提交来保证位移提交成功。
            kafkaConsumer.commitSync(offsetInfo);
            kafkaConsumer.close();
        }
    }
}
