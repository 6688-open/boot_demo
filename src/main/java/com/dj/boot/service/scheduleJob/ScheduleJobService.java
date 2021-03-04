package com.dj.boot.service.scheduleJob;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.pojo.ScheduleJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.util.List;

public interface ScheduleJobService extends IService<ScheduleJob> {

    ScheduleJob getJobEntityById(Integer id);

    List<ScheduleJob> getAllJobEntity();

    //获取JobDataMap.(Job参数对象)
    JobDataMap getJobDataMap(ScheduleJob job);

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map);

    //获取Trigger (Job的触发器,执行规则)
    Trigger getTrigger(ScheduleJob job);
    //获取JobKey,包含Name和Group
    JobKey getJobKey(ScheduleJob job);
}
