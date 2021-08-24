package com.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/14
 */
public class Task1 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Object jobVo = jobDataMap.get("jobVo");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " run ################## " + new Date());
        }
    }

}