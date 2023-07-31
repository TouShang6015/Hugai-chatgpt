package com.hugai.modules.system.service;

import com.hugai.modules.system.entity.model.SysFileConfigModel;
import com.org.bebas.mapper.service.IService;

/**
 * 文件上传配置 业务接口
 *
 * @author WuHao
 * @date 2023-05-29
 */
public interface SysFileConfigService extends IService<SysFileConfigModel> {

    /**
     * 获取当前文件配置路径
     *
     * @return
     */
    String getFileConfigPath();

}
