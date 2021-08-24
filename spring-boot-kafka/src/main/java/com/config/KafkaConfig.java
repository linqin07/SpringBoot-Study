package com.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/26
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String borkers;

    public String getBorkers() {
        return borkers;
    }

    public void setBorkers(String borkers) {
        this.borkers = borkers;
    }

    @Bean
    public AdminClient createKafkaAdminClient() {
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, borkers);
        return AdminClient.create(properties);
    }
}
