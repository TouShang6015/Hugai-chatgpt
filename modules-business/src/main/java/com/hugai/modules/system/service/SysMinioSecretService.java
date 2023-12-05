package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.model.SysMinioSecretModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/7/19 17:19
 */
public interface SysMinioSecretService extends IService<SysMinioSecretModel> {

    /**
     * 获取当前文件服务策略的秘钥信息
     *
     * @return
     */
    SysMinioSecretModel getStrategySecretConfig();

    /**
     * 获取指定文件服务策略的秘钥信息
     *
     * @param strategyValue
     * @return
     */
    SysMinioSecretModel getStrategySecretConfig(String strategyValue);

    /**
     * 从缓存中获取list集合
     *
     * @return
     */
    List<SysMinioSecretModel> cacheGetAllList();

    /**
     * 删除缓存
     */
    void cacheRemove();

}
