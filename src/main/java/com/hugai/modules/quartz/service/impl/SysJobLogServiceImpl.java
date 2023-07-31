package com.hugai.modules.quartz.service.impl;

import com.hugai.modules.quartz.entity.model.SysJobLogModel;
import com.hugai.modules.quartz.mapper.SysJobLogMapper;
import com.hugai.modules.quartz.service.ISysJobLogService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度日志表 业务实现类
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLogModel> implements ISysJobLogService {

    @Override
    public void cleanJobLog() {
        baseMapper.cleanJobLog();
    }
}
