package com.dj.boot.service.scheduleJob.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.configuration.quartz.JopManagementFactory;
import com.dj.boot.mapper.scheduleJob.ScheduleJobMapper;
import com.dj.boot.pojo.ScheduleJob;
import com.dj.boot.service.scheduleJob.ScheduleJobService;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName JopEntryServiceImpl
 * @Description TODO
 * @Author wj
 * @Date 2019/12/11 18:50
 * @Version 1.0
 **/
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {


    @Override
    public ScheduleJob getJobEntityById(Integer id) {
        return this.getById(id);
    }

    @Override
    public List<ScheduleJob> getAllJobEntity() {
        return this.list();
    }

    @Override
    public JobDataMap getJobDataMap(ScheduleJob job) {
        JobDataMap map = new JobDataMap();
        map.put("beanName", job.getBeanName());
        map.put("methodName", job.getMethodName());
        map.put("cronExpression", job.getCron());
        map.put("param", job.getParam());
        map.put("description", job.getDescription());
        map.put("vmParam", job.getVmParam());
        map.put("status", job.getStatus());
        return map;
    }

    @Override
    public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(JopManagementFactory.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    @Override
    public Trigger getTrigger(ScheduleJob job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getBeanName(), job.getMethodName())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    @Override
    public JobKey getJobKey(ScheduleJob job) {
        return JobKey.jobKey(job.getBeanName(), job.getMethodName());
    }
}
