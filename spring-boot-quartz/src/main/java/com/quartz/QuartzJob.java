package com.quartz;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/14
 */
public class QuartzJob {
    public static final Integer STATUS_RUNNING = 1;
    public static final Integer STATUS_NOT_RUNNING = 0;
    public static final Integer CONCURRENT_IS = 1;
    public static final Integer CONCURRENT_NOT = 0;

    private String jobId;
    /**
     * cron 表达式
     */
    private String cronExpression;
    /**
     * 任务调用的方法名
     */
    private String methodName;
    /**
     * 任务是否有状态
     */
    private Integer isConcurrent;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名，完全限定名
     */
    private String beanName;
    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 任务状态
     */
    private Integer jobStatus;
    private String springBean;
    /**
     * 任务名
     */
    private String jobName;


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Integer isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getSpringBean() {
        return springBean;
    }

    public void setSpringBean(String springBean) {
        this.springBean = springBean;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
