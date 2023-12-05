package com.hugai.modules.config.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.modules.config.mapper.ChatKeysMapper;
import com.hugai.modules.config.service.IChatKeysService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 对话秘钥池 业务实现类
 *
 * @author wuhao
 * @date 2023-11-27
 */
@Slf4j
@Service
public class ChatKeysServiceImpl extends ServiceImpl<ChatKeysMapper, ChatKeysModel> implements IChatKeysService {

    private final static String USABLE_KEY = ModelUtil.modelMainKey(ChatKeysModel.class) + "USABLE:";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<ChatKeysModel> getAbleByChatSdkId(Long chatSdkId) {
        String key = USABLE_KEY + chatSdkId;
        List<ChatKeysModel> cacheList = redisUtil.getCacheList(key);
        if (CollUtil.isEmpty(cacheList)) {
            cacheList = super.lambdaQuery().eq(ChatKeysModel::getChatSdkId, chatSdkId).eq(ChatKeysModel::getEnableStatus, Constants.EnableStatus.USABLE).list();
            redisUtil.setCacheList(key, cacheList);
        }
        return cacheList;
    }

    @Override
    public void removeByApiToken(String token) {
        if (StrUtil.isEmpty(token))
            return;
        OR.run(super.lambdaQuery().eq(ChatKeysModel::getApiToken, token).one(), Objects::nonNull, model -> {
            super.lambdaUpdate().set(ChatKeysModel::getRemark, "帐号异常")
                    .set(ChatKeysModel::getEnableStatus, Constants.EnableStatus.DISABLE)
                    .eq(ChatKeysModel::getId, model.getId())
                    .update();
            redisUtil.deleteObject(USABLE_KEY + model.getChatSdkId());
        });
    }

    @Override
    public void flushCache() {
        String key = ModelUtil.modelMainKey(ChatKeysModel.class) + "*";
        redisUtil.deleteLike(key);
        log.info("[清除缓存] - key: {}", key);
    }

}
