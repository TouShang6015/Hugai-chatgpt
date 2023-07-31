package com.hugai.modules.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugai.modules.quartz.entity.model.SysJobLogModel;
import org.apache.ibatis.annotations.Update;

/**
 * 定时任务调度日志表 持久层接口
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLogModel> {

    @Update("truncate table sys_job_log")
    void cleanJobLog();
}
