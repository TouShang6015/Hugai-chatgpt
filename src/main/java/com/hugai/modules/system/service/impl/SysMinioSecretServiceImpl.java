package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.hugai.modules.system.entity.model.SysMinioSecretModel;
import com.hugai.modules.system.mapper.SysMinioSecretMapper;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.system.service.SysMinioSecretService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WuHao
 * @since 2023/7/19 17:20
 */
@Service
public class SysMinioSecretServiceImpl extends ServiceImpl<SysMinioSecretMapper, SysMinioSecretModel> implements SysMinioSecretService {

    private final static String CACHE_KEY = ModelUtil.modelMainKey(SysMinioSecretModel.class);

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private IBaseResourceConfigService resourceConfigService;

    /**
     * 获取当前文件服务策略的秘钥信息
     *
     * @return
     */
    @Override
    public SysMinioSecretModel getStrategySecretConfig() {
        String fileSaveStrategy = resourceConfigService.getResourceMain().getFileSaveStrategy();
        return this.getStrategySecretConfig(fileSaveStrategy);
    }

    /**
     * 获取指定文件服务策略的秘钥信息
     *
     * @param strategyValue
     * @return
     */
    @Override
    public SysMinioSecretModel getStrategySecretConfig(String strategyValue) {
        Assert.notEmpty(strategyValue);
        return OptionalUtil.ofNullList(this.cacheGetAllList()).stream()
                .filter(item -> item.getUniqueKey().equals(strategyValue))
                .findFirst()
                .orElse(null);
    }

    /**
     * 从缓存中获取list集合
     *
     * @return
     */
    @Override
    public List<SysMinioSecretModel> cacheGetAllList() {
        String key = CACHE_KEY + "ALL-LIST";
        List<SysMinioSecretModel> list = null;
        List<SysMinioSecretModel> cacheList = redisUtil.getCacheList(key);
        if (CollUtil.isEmpty(cacheList)) {
            List<SysMinioSecretModel> allList = super.list();
            redisUtil.setCacheList(key, allList);
            list = allList;
        } else {
            list = cacheList;
        }
        return list;
    }

    /**
     * 删除缓存
     */
    @Override
    public void cacheRemove() {
        String key = CACHE_KEY + "ALL-LIST";
        redisUtil.deleteObject(key);
    }


}
