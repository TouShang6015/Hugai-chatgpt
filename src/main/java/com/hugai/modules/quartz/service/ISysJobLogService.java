package com.hugai.modules.quartz.service;


import com.hugai.modules.quartz.entity.model.SysJobLogModel;
import com.org.bebas.mapper.service.IService;

/**
 * 定时任务调度日志表 业务接口
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 */
public interface ISysJobLogService extends IService<SysJobLogModel> {

    void cleanJobLog();
}
