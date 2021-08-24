package com.controller;

import com.quartz.JobService;
import com.quartz.JobVo;
import com.quartz.Task1;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/15
 */
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping("/")
    public void test() throws SchedulerException, InterruptedException {

        JobVo jobVo = new JobVo();
        jobVo.setJobId("1");
        jobVo.setJobName("test");
        jobVo.setJobGroup("testGroup");
        jobVo.setJobStatus("1");
        jobVo.setCronExpression("1 * * * * ?");
        jobVo.setDesc("测试");



        jobService.addJob(jobVo, Task1.class);
        List<JobVo> allJob = jobService.getAllJob();
        allJob.forEach(System.out::println);

        Thread.sleep(100000);
        jobService.runAJobNow(jobVo);
    }


}
