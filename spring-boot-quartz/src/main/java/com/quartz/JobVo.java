package com.quartz;

import java.io.Serializable;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/14
 */
public class JobVo implements Serializable {
    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "2";

    /** 任务id **/
    private String jobId;
    /** 任务名称 **/
    private String jobName;
    /** 任务分组 **/
    private String jobGroup;
    /** 任务状态 	1启用 2禁用 **/
    private String jobStatus;
    /** 任务运行时间表达式 **/
    private String cronExpression;
    /** 任务描述 **/
    private String desc;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "JobVo{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
