package com.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @Description:
 * @author: LinQin
 * @date: 2020/07/15
 */
public class KafkaTest {
    public static void main(String[] args) {
        String topic = args[2];

        String message = LocalDateTime.now() + "我是测试数据";
        Properties props = new Properties();
        props.put("bootstrap.servers", args[3]);
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        //props.put("partitioner.class", HashPartitioner.class.getName());

        int num = Integer.parseInt(args[0]);
        int second = Integer.parseInt(args[1]);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                Producer<String, String> producer = new KafkaProducer<String, String>(props);
                while (true) {
                    try {
                        Thread.sleep(1000 * second);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    producer.send(new ProducerRecord<String, String>(topic, message));
                }
            }).start();
        }
        // Producer<String, String> producer = new KafkaProducer<String, String>(props);
        // for (int i = 0; i < 10; i++) {
        //     producer.send(new ProducerRecord<String, String>(topic, message));
        // }
        // producer.close();
    }
}