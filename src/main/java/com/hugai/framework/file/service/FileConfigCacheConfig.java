package com.hugai.framework.file.service;


import com.hugai.modules.system.entity.model.SysFileConfigModel;

/**
 * @author WuHao
 * @since 2023/5/29 16:39
 */
public interface FileConfigCacheConfig {

    /**
     * 设置sysFileConfigModel
     *
     * @param fileConfig
     */
    void setFileConfig(SysFileConfigModel fileConfig);

    /**
     * 获取配置
     *
     * @return
     */
    SysFileConfigModel getFileConfig();

}
