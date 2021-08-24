package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description: spring自带的异步线程池
 * @author: LinQin
 * @date: 2018/11/17
 */
public class AsyncConfig {

     /*

    此处成员变量应该使用@Value从配置中读取

     */

    private int corePoolSize = 10;

    private int maxPoolSize = 200;

    private int queueCapacity = 10;

    @Bean
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);

        executor.setMaxPoolSize(maxPoolSize);

        executor.setQueueCapacity(queueCapacity);

        executor.initialize();

        return executor;

    }

}