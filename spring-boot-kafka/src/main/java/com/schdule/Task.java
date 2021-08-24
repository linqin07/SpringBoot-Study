package com.schdule;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.producer.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @author: LinQin
 * @date: 2020/10/29
 */
@Component
public class Task {

    @Value("${kafka.borkers}")
    private String borkers;

    @Scheduled(cron = "*/30 * * * * *")
    public void task() {
        System.out.println("定时执行任务！！");
        Map<String, String> data = Maps.newHashMap();
        data.put("ivorynew5", "ivorynew5 [1029/095526.010:ERROR:directory_reader_win.cc(43)] FindFirstFile: 系统找不到指定的路径。 (0x3)\n");
        data.put("ivorynew4", "ivorynew4 [1029/095526.010:ERROR:directory_reader_win.cc(43)] FindFirstFile: 系统找不到指定的路径。 (0x3)\n");
        data.put("ivorynew3", "ivorynew3 [1029/095526.010:ERROR:directory_reader_win.cc(43)] FindFirstFile: 系统找不到指定的路径。 (0x3)\n");
        data.put("ivorynew2", "ivorynew2 [1029/095526.010:ERROR:directory_reader_win.cc(43)] FindFirstFile: 系统找不到指定的路径。 (0x3)\n");
        data.put("ivorynew1", "ivorynew1 [1029/095526.010:ERROR:directory_reader_win.cc(43)] FindFirstFile: 系统找不到指定的路径。 (0x3)\n");
        setTopicData(data);

    }

    public void setTopicData(Map<String, String> data) {
        Properties props = new Properties();
        props.put("bootstrap.servers", borkers);
        props.put("acks", "all");// 判断是不是成功发送了,“all”将会阻塞消息，这种设置性能最低，但是是最可靠的
        props.put("retries", 0); // 失败重试次数
        props.put("batch.size", 16384);// 缓存的大小,越大越吃内存
        props.put("linger.ms", 100);// 默认缓冲逗留时间大小,到点可立即发送
        props.put("buffer.memory", 33554432);// 控制生产者可用的缓存总量
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        data.keySet().forEach(topic -> {
            producer.send(new ProducerRecord<String, String>(topic, "key", data.get(topic).toString()),
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
