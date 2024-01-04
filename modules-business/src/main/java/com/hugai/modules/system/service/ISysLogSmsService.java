package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.model.SysLogSmsModel;
import com.hugai.common.webApi.log.SysLogSmsWebApi;
import com.org.bebas.mapper.service.IService;

/**
 * 验证码日志 业务接口
 *
 * @author wuhao
 * @date 2023-12-07
 */
public interface ISysLogSmsService extends IService<SysLogSmsModel>, SysLogSmsWebApi {

    @Override
    default void saveLog(SysLogSmsModel param) {
        save(param);
    }
}
