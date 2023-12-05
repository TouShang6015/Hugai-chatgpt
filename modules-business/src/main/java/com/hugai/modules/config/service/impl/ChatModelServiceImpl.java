package com.hugai.modules.config.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.modules.config.mapper.ChatModelMapper;
import com.hugai.modules.config.service.IChatModelService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 对话模型管理 业务实现类
 *
 * @author wuhao
 * @date 2023-11-27
 */
@Service
public class ChatModelServiceImpl extends ServiceImpl<ChatModelMapper, ChatModelModel> implements IChatModelService {

    private final static String MAIN_KEY = ModelUtil.modelMainKey(ChatModelModel.class);

    @Resource
    private RedisUtil redisUtil;

    @Override
    public ChatModelModel getByUniqueKey(String key) {
        if (StrUtil.isEmpty(key))
            return null;
        String redisKey = MAIN_KEY + "uniqueKey:";
        ChatModelModel cacheObject = redisUtil.getCacheObject(redisKey);
        if (Objects.isNull(cacheObject)) {
            cacheObject = super.lambdaQuery().eq(ChatModelModel::getUniqueKey, key).one();
            redisUtil.setCacheObject(redisKey, cacheObject);
        }
        return cacheObject;
    }

    @Override
    public List<ChatModelModel> getAllList() {
        String redisKey = MAIN_KEY + "all:";
        List<ChatModelModel> cacheList = redisUtil.getCacheList(redisKey);
        if (CollUtil.isEmpty(cacheList)) {
            cacheList = super.list();
            redisUtil.setCacheList(redisKey, cacheList);
        }
        return cacheList;
    }

    @Override
    public void flushCache() {
        String key = ModelUtil.modelMainKey(ChatModelModel.class) + "*";
        redisUtil.deleteLike(key);
        log.info("[清除缓存] - key: {}", key);
    }
}
