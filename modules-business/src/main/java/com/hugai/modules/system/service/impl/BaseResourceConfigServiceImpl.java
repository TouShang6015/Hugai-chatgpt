package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.hugai.common.support.init.InitCache;
import com.hugai.common.modules.entity.system.model.BaseResourceConfigModel;
import com.hugai.modules.system.manager.ResourceModelManager;
import com.hugai.modules.system.mapper.BaseResourceConfigMapper;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统参数配置表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-21 09:02:14
 */
@Service
public class BaseResourceConfigServiceImpl extends ServiceImpl<BaseResourceConfigMapper, BaseResourceConfigModel> implements IBaseResourceConfigService, InitCache {

    private final String KEY = ModelUtil.modelMainKey(BaseResourceConfigModel.class) + "@configKey-";

    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据configKey查询单个信息
     *
     * @param configKey
     * @return
     */
    @Override
    public BaseResourceConfigModel queryByConfigKey(String configKey) {
        if (StrUtil.isEmpty(configKey))
            return null;
        BaseResourceConfigModel model = ResourceModelManager.get(configKey);
        String key = KEY + configKey;
        if (Objects.isNull(model)) {
            model = redisUtil.getCacheObject(key);
        }
        if (ObjectUtil.isNull(model)) {
            model = lambdaQuery().eq(BaseResourceConfigModel::getConfigKey, configKey).one();
            // 存redis
            BaseResourceConfigModel finalModel = model;
            OR.run(ObjectUtil.isNotNull(model), () -> {
                redisUtil.setCacheObject(key, finalModel);
                ResourceModelManager.add(configKey, finalModel);
            });
        }
        return model;
    }

    /**
     * 根据configKey获取configValue
     *
     * @param configKey
     * @return
     */
    @Override
    public String queryValueByConfigKey(String configKey) {
        BaseResourceConfigModel resourceConfigModel = this.queryByConfigKey(configKey);
        if (ObjectUtil.isNull(resourceConfigModel))
            return null;
        return resourceConfigModel.getResourceValue();
    }

    @Override
    public <T> T queryValueByConfigKey(String configKey, Class<T> tClass, Boolean cache) {
        String resourceValue;
        if (cache) {
            resourceValue = this.queryValueByConfigKey(configKey);
        } else {
            resourceValue = this.lambdaQuery().eq(BaseResourceConfigModel::getConfigKey, configKey).select(BaseResourceConfigModel::getResourceValue).one().getResourceValue();
        }
        if (StrUtil.isEmpty(resourceValue))
            return null;
        return JSONObject.parseObject(resourceValue, tClass);
    }

    /**
     * 根据configKey编辑configValue
     *
     * @param param
     * @return
     */
    @Override
    public boolean editByConfigKey(BaseResourceConfigModel param) {
        String configKey = param.getConfigKey();
        String resourceValue = param.getResourceValue();
        if (StrUtil.isEmpty(configKey))
            return false;
        String key = KEY + configKey;
        BaseResourceConfigModel model = lambdaQuery().eq(BaseResourceConfigModel::getConfigKey, configKey).one();
        if (ObjectUtil.isNull(model)) {
            return false;
        }
        model.setResourceValue(resourceValue);
        if (this.updateById(model)) {
            // 设置redis
            redisUtil.deleteObject(key);
            ResourceModelManager.remove(configKey);
            this.queryByConfigKey(configKey);
        }
        return true;
    }

    /**
     * 初始化缓存
     */
    @Override
    public void runInitCache() {
        List<BaseResourceConfigModel> list = this.list();
        if (CollUtil.isEmpty(list))
            return;
        // 清空缓存
        List<String> redisConfigKeyList = list.stream().map(x -> KEY + x.getConfigKey()).distinct().collect(Collectors.toList());
        redisUtil.deleteObject(redisConfigKeyList);
        // 刷新缓存
        list.forEach(item -> {
            redisUtil.setCacheObject(KEY + item.getConfigKey(), item);
            ResourceModelManager.add(item.getConfigKey(), item);
        });
    }

}
