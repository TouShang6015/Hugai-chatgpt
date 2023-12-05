package com.hugai.modules.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.enums.ChannelEnum;
import com.hugai.framework.asyncMessage.annotation.MessageListener;
import com.hugai.common.modules.entity.system.model.SysOperLogModel;
import com.hugai.modules.system.mapper.SysOperLogMapper;
import com.hugai.modules.system.service.ISysOperLogService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * 操作日志记录 业务实现类
 *
 * @author WuHao
 * @date 2022-06-22 22:35:41
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLogModel> implements ISysOperLogService {

    @MessageListener(ChannelEnum.log_handle)
    @Override
    public void listenerInsertOperLog(byte[] data) {
        SysOperLogModel sysOperLogModel = JSON.parseObject(new String(data, Charset.defaultCharset()), SysOperLogModel.class);
        if (super.save(sysOperLogModel)) {
            log.info("[操作日志记录] 新增操作日志。");
        } else {
            log.error("[操作日志记录] 新增日志失败。");
        }
    }

    /**
     * 清空日志
     */
    @Override
    public void cleanOperlog() {
        baseMapper.cleanOperlog();
    }
}
