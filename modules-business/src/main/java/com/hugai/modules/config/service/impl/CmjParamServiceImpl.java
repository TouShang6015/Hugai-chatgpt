package com.hugai.modules.config.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.modules.entity.config.model.CmjParamModel;
import com.hugai.modules.config.mapper.CmjParamMapper;
import com.hugai.modules.config.service.ICmjParamService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * mj参数配置 业务实现类
 *
 * @author wuhao
 * @date 2023-09-25
 */
@Service
public class CmjParamServiceImpl extends ServiceImpl<CmjParamMapper, CmjParamModel> implements ICmjParamService {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 缓存获取全部列表记录
     *
     * @return
     */
    @Override
    public List<CmjParamModel> cacheGetAll() {
        List<CmjParamModel> cacheList = redisUtil.getCacheList(CACHE_KEY);
        if (CollUtil.isEmpty(cacheList)) {
            cacheList = super.list();
            redisUtil.setCacheList(CACHE_KEY, cacheList);
        }
        return cacheList;
    }

    /**
     * 根据唯一标识获取model
     *
     * @param key
     * @return
     */
    @Override
    public CmjParamModel cacheGetByKey(String key) {
        List<CmjParamModel> models = this.cacheGetAll();
        if (CollUtil.isEmpty(models))
            return null;
        CmjParamModel matchModel = models.stream().filter(item -> item.getUniqueKey().equals(key)).findFirst().orElse(null);
        if (Objects.isNull(matchModel))
            return null;
        return matchModel;
    }

    /**
     * 清楚缓存
     */
    @Override
    public void cacheClear() {
        redisUtil.deleteObject(CACHE_KEY);
        redisUtil.setCacheList(CACHE_KEY, super.list());
    }
}
