package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Description: 支持异步。
 * author: LinQin
 * date: 2018/07/13
 */
//@Component
//@Async
public class Task {
    Logger loger = LoggerFactory.getLogger(this.getClass());

    // @Scheduled(cron = "*/5 * * ? * *")

    /**
     * fixedRate上一个调用开始后再次调用的延时（不用等待上一次调用完成）
     */
    // @Scheduled(fixedRate = 1 * 1000)

    /**
     * 等到方法执行完成后延迟配置的时间再次执行该方法
     */

    @Scheduled(fixedDelay = 1 * 1000)
    public void Task() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loger.info("任务0   " + System.currentTimeMillis());

    }

    @Scheduled(cron = "*/10 * * ? * *")
    public void Task1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loger.info("任务1 " + System.currentTimeMillis());
    }
}
