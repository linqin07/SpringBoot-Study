package com.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description: kafka分区rebalace监听器，该类中设置一个 标识，用来处理rebalace后可能会导致的一些问题，如位移提交失败导致的重复汇
 * @author: LinQin
 * @date: 2019/11/15
 */
@Slf4j
public class KafkaRebalanceListener implements ConsumerRebalanceListener {

    private AtomicBoolean rebalanceFlag;

    public KafkaRebalanceListener(AtomicBoolean rebalanceFlag) {
        this.rebalanceFlag = rebalanceFlag;
    }

    /**
     * 发生rebalance时更改标志，在创建消费者的时候指定使用该监听器
     * @param partitions
     */
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        partitions.forEach(partition -> {
            log.info("topic {} - partition {} revoked.", partition.topic(), partition.partition());
            rebalanceFlag.set(true);
        });
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        partitions.forEach(partition -> {
            log.info("topic {} - partition {} assigned.", partition.topic(), partition.partition());
        });
    }
}
