package com.hugai.modules.quartz.utils;

import com.hugai.modules.quartz.entity.model.SysJobModel;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author ruoyi
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJobModel sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
