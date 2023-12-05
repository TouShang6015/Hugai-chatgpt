package com.hugai.modules.quartz.utils;

import com.hugai.common.constants.Constants;
import com.hugai.modules.quartz.constants.ScheduleConstants;
import com.hugai.modules.quartz.entity.model.SysJobLogModel;
import com.hugai.modules.quartz.entity.model.SysJobModel;
import com.hugai.modules.quartz.service.ISysJobLogService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.bean.BeanUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author ruoyi
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        SysJobModel sysJob = new SysJobModel();
        BeanUtil.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
        try {
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJobModel sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void after(JobExecutionContext context, SysJobModel sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLogModel sysJobLog = new SysJobLogModel();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        long runMs = new Date().getTime() - startTime.getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus(Constants.Status.NO_NORMAL);
            String errorMsg = StringUtils.substring(e.getMessage(), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus(Constants.Status.NORMAL);
        }

        // 写入数据库当中
        if (Constants.BOOLEAN.TRUE.equals(sysJob.getIfSaveLog())){
            SpringUtils.getBean(ISysJobLogService.class).save(sysJobLog);
        }
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJobModel sysJob) throws Exception;
}
