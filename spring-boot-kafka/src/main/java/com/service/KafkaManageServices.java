package com.service;

import com.config.KafkaConfig;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/26
 */
@Service
public class KafkaManageServices {
    @Autowired
    private AdminClient adminClient;

    @Autowired
    private KafkaConfig kafkaConfig;

    public DescribeClusterResult describeClusterResult() {
        DescribeClusterResult describeCluster = adminClient.describeCluster();
        return describeCluster;
    }

    public List<String> listAllTopics() throws ExecutionException, InterruptedException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        Set<String> strings = listTopicsResult.names().get();
        return strings.stream().sorted().collect(Collectors.toList());
    }

    /**
     * 创建topic
     * @param topic
     * @param numPartitions 分区数
     * @param replicationFactor 复制因子
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void createTopic(String topic, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topic, numPartitions, replicationFactor);
        CreateTopicsResult topics = adminClient.createTopics(Arrays.asList(newTopic));
        topics.all().get();
    }

    /**
     * 删除topic
     * @param topics
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void deleteTopic(List<String> topics) throws ExecutionException, InterruptedException {
        KafkaFuture<Void> future = adminClient.deleteTopics(topics).all();
        future.get();
    }

    public Map<String, TopicDescription> describeTopics(List<String> topics) throws ExecutionException, InterruptedException {
        KafkaFuture<Map<String, TopicDescription>> ret = adminClient.describeTopics(topics).all();
        return ret.get();
    }

    public TopicDescription describeTopic(String topics) throws ExecutionException, InterruptedException {
        return describeTopics(Lists.newArrayList(topics)).get(topics);
    }

    public List<String> getTopicData(String topic, int count) {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConfig.getBorkers());
        props.put("group.id", "test"); //消费群id
        props.put("enable.auto.commit", "true"); //消费者偏移的频率以毫秒为单位自动提交给Kafka，
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅（可以多个）
        consumer.subscribe(Arrays.asList(topic));

        List<String> messages = new ArrayList<>(count);
        long start = System.currentTimeMillis();
        try {
            while ((System.currentTimeMillis() - start) < 2000) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
                for (ConsumerRecord<String, String> record : records){
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    messages.add(record.value());
//                    if (messages.size() == count) {
//                    }
                    return messages;

                }
            }
            return messages;

        } finally {
            consumer.close();
        }
    }

    public void setTopicData(String topic, List<Map<String, Object>> data) {
        Properties props = new Properties();
        props.put("bootstrap.servers",  kafkaConfig.getBorkers());
        props.put("acks", "all");// 判断是不是成功发送了,“all”将会阻塞消息，这种设置性能最低，但是是最可靠的
        props.put("retries", 0); // 失败重试次数
        props.put("batch.size", 16384);// 缓存的大小,越大越吃内存
        props.put("linger.ms", 1);// 默认缓冲逗留时间大小,到点可立即发送
        props.put("buffer.memory", 33554432);// 控制生产者可用的缓存总量
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        data.forEach(item -> {
            producer.send(new ProducerRecord<String, String>(topic, "key", item.toString()),
                    new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            if (exception == null)
                                System.out.println("获取消息发送成功");
                        }
                    });
        });

        producer.close();

    }

}
