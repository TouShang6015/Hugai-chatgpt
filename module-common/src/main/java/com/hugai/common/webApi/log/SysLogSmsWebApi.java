package com.hugai.common.webApi.log;


import com.hugai.common.modules.entity.system.model.SysLogSmsModel;

/**
 * @author WuHao
 * @since 2023/12/7 10:43
 */
public interface SysLogSmsWebApi {

    void saveLog(SysLogSmsModel param);

}
