package com.hugai.modules.quartz.service.impl;

import com.hugai.modules.quartz.constants.ScheduleConstants;
import com.hugai.modules.quartz.entity.model.SysJobModel;
import com.hugai.config.exception.TaskException;
import com.hugai.modules.quartz.mapper.SysJobMapper;
import com.hugai.modules.quartz.service.ISysJobService;
import com.hugai.modules.quartz.utils.CronUtils;
import com.hugai.modules.quartz.utils.ScheduleUtils;
import com.org.bebas.mapper.cache.ServiceImpl;
import lombok.SneakyThrows;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 定时任务调度表 业务实现类
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJobModel> implements ISysJobService {

    @Resource
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void initQuartz() throws SchedulerException, TaskException {
        scheduler.clear();
        List<SysJobModel> jobList = super.list();
        for (SysJobModel job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    @SneakyThrows
    @Override
    public boolean save(SysJobModel entity) {
        entity.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        if (super.save(entity)) {
            ScheduleUtils.createScheduleJob(scheduler, entity);
        }
        return true;
    }

    @SneakyThrows
    @Override
    public boolean updateById(SysJobModel entity) {
        SysJobModel properties = super.getById(entity.getId());
        if (super.updateById(entity)) {
            updateSchedulerJob(entity, properties.getJobGroup());
        }
        return true;
    }

    @SneakyThrows
    @Override
    public boolean removeByIds(Collection<?> list) {
        List<SysJobModel> sysJobList = super.lambdaQuery().in(SysJobModel::getId, list).list();
        for (SysJobModel job : sysJobList) {
            Long jobId = job.getId();
            String jobGroup = job.getJobGroup();
            if (super.removeById(jobId)) {
                scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            }
        }
        return true;
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     */
    @SneakyThrows
    @Override
    public Boolean changeStatus(SysJobModel job) {
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            return resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            return pauseJob(job);
        }
        return false;
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    @Override
    public Boolean pauseJob(SysJobModel job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        if (super.updateById(job)) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return true;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    @Override
    public Boolean resumeJob(SysJobModel job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        if (super.updateById(job)) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return true;
    }

    /**
     * 立即执行任务
     *
     * @param job
     */
    @SneakyThrows
    @Override
    public void run(SysJobModel job) {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        SysJobModel properties = super.getById(jobId);
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }


    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJobModel job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }
}
