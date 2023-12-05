package com.hugai.modules.config.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.modules.entity.config.model.ChatSdkHostModel;
import com.hugai.modules.config.mapper.ChatSdkHostMapper;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对话镜像地址管理 业务实现类
 *
 * @author wuhao
 * @date 2023-11-27
 */
@Service
public class ChatSdkHostServiceImpl extends ServiceImpl<ChatSdkHostMapper, ChatSdkHostModel> implements IChatSdkHostService {

    private final static String USABLE_KEY = ModelUtil.modelMainKey(ChatSdkHostModel.class) + "USABLE:";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<ChatSdkHostModel> getUsableByChatSdkId(Long chatSdkId) {
        String key = USABLE_KEY + chatSdkId;
        List<ChatSdkHostModel> cacheList = redisUtil.getCacheList(key);
        if (CollUtil.isEmpty(cacheList)) {
            cacheList = super.lambdaQuery().eq(ChatSdkHostModel::getEnableStatus, Constants.EnableStatus.USABLE).eq(ChatSdkHostModel::getChatSdkId, chatSdkId).list();
            redisUtil.setCacheList(key, cacheList);
        }
        return cacheList;
    }

    @Override
    public void flushCache() {
        String key = ModelUtil.modelMainKey(ChatSdkHostModel.class) + "*";
        redisUtil.deleteLike(key);
        log.info("[清除缓存] - key: {}", key);
    }
}
