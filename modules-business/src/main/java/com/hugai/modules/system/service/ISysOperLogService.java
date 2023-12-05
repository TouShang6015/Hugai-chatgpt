package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.model.SysOperLogModel;
import com.org.bebas.mapper.service.IService;

/**
 * 操作日志记录 业务接口
 *
 * @author WuHao
 * @date 2022-06-22 22:35:41
 */
public interface ISysOperLogService extends IService<SysOperLogModel> {

    void listenerInsertOperLog(byte[] data);

    /**
     * 清空日志
     */
    void cleanOperlog();
}
