package com.config;

import com.quartz.JobService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description: 项目启动重新加载数据库保存的定时任务
 * @author: LinQin
 * @date: 2019/02/15
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    private JobService jobService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // todo 查数据库获取开启的定时任务列表
        System.out.println("查数据库获取开启的定时任务列表");
//        jobService.addJob();
    }
}
