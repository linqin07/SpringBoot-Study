package com.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/14
 */
@Service
public class JobService {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    /**
     * 更改任务状态
     *
     * @throws SchedulerException
     */
    public void changeStatus(JobVo job, Class<?> clazz) throws SchedulerException {
        if (job.getJobStatus().equals(JobVo.STATUS_NOT_RUNNING)) {
            deleteJob(job);
            job.setJobStatus(JobVo.STATUS_NOT_RUNNING);
        } else if (job.getJobStatus().equals(JobVo.STATUS_RUNNING)) {
            job.setJobStatus(JobVo.STATUS_RUNNING);
            addJob(job, clazz);
        }
    }

    /**
     * 判断任务是否存在
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    public boolean existJob(JobVo job) throws SchedulerException {
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return false;
        }
        return true;
    }

    /**
     * 添加任务
     *
     * @param job
     * @param clazz
     *            任务实现类
     * @throws SchedulerException
     */
    public void addJob(JobVo job, Class clazz) throws SchedulerException {
        if (job == null || !JobVo.STATUS_RUNNING.equals(job.getJobStatus())) {
            return;
        }

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//		job.setCronExpression("0 0/1 * * * ?");
        // 不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobId(), job.getJobGroup()).build();
            jobDetail.getJobDataMap().put("jobVo", job);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId(), job.getJobGroup())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            //报表定时计划可能更新JobDetail内容，须要先删除
//            if(ReportPlanJobServiceBean.class.equals(clazz)){
//                this.deleteJob(job);
//                this.addJob(job, clazz);
//            }

            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<JobVo> getAllJob() throws SchedulerException {
        //Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<JobVo> jobList = new ArrayList<JobVo>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                JobVo job = new JobVo();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<JobVo> getRunningJob() throws SchedulerException {
        //Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<JobVo> jobList = new ArrayList<JobVo>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            JobVo job = new JobVo();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param jobVo
     * @throws SchedulerException
     */
    public void pauseJob(JobVo jobVo) throws SchedulerException {
        // System.out.println("======暂停任务["+jobVo.getJobName()+"]======");
        //Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobKey jobKey = JobKey.jobKey(jobVo.getJobId(), jobVo.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param jobVo
     * @throws SchedulerException
     */
    public void resumeJob(JobVo jobVo) throws SchedulerException {
        // System.out.println("======恢复任务["+jobVo.getJobName()+"]======");
        //Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobKey jobKey = JobKey.jobKey(jobVo.getJobId(), jobVo.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param jobVo
     * @throws SchedulerException
     */
    public void deleteJob(JobVo jobVo) throws SchedulerException {
//        log.info("======删除任务["+jobVo.getJobName()+"]======");
        //Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobKey jobKey = JobKey.jobKey(jobVo.getJobId(), jobVo.getJobGroup());
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobVo.getJobId(), jobVo.getJobGroup());
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即执行job
     *
     * @param jobVo
     * @throws SchedulerException
     */
    public void runAJobNow(JobVo jobVo) throws SchedulerException {
        // System.out.println("======立即执行任务["+jobVo.getJobName()+"]======");
        if (existJob(jobVo)) {
            //Scheduler scheduler = schedulerFactoryBean.getScheduler();
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = JobKey.jobKey(jobVo.getJobId(), jobVo.getJobGroup());
            scheduler.triggerJob(jobKey);
        } else {
            // System.out.println("======任务["+jobVo.getJobName()+"]不存在======");
            // todo
//            addJob(jobVo, AlarmJob.class);
        }

    }

    /**
     * 更新job时间表达式
     *
     * @param jobVo
     * @throws SchedulerException
     */
    public void updateJobCron(JobVo jobVo) throws SchedulerException {
        //Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobVo.getJobId(), jobVo.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            // todo
//            addJob(jobVo, AlarmJob.class);
            return;
        }
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

}
