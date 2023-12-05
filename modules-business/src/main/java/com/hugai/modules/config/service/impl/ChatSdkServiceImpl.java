package com.hugai.modules.config.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.modules.config.mapper.ChatSdkMapper;
import com.hugai.modules.config.service.IChatSdkService;
import org.springframework.stereotype.Service;
import com.org.bebas.mapper.cache.ServiceImpl;

/**
 * 对话第三方平台管理 业务实现类
 *
 * @author wuhao
 * @date 2023-11-27
 */
@Service
public class ChatSdkServiceImpl extends ServiceImpl<ChatSdkMapper, ChatSdkModel> implements IChatSdkService {

    @Override
    public ChatSdkModel getByUniqueKey(String key) {
        if (StrUtil.isEmpty(key))
            return null;
        ChatSdkModel one = super.lambdaQuery().eq(ChatSdkModel::getUniqueKey, key).one();
        return one;
    }

}
