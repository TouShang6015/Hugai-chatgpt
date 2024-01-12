package com.hugai.modules.config.service;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.common.webApi.configChat.ChatKeysWebApi;
import com.org.bebas.mapper.service.IService;
import com.org.bebas.utils.OptionalUtil;

import java.util.List;

/**
 * 对话秘钥池 业务接口
 *
 * @author wuhao
 * @date 2023-11-27
 */
public interface IChatKeysService extends IService<ChatKeysModel>, ChatKeysWebApi {

    @Override
    default ChatKeysModel queryByApiToken(String apiToken) {
        if (StrUtil.isEmpty(apiToken))
            return null;
        return OptionalUtil.ofNullList(
                this.lambdaQuery().eq(ChatKeysModel::getApiToken, apiToken).list()
        ).stream().findFirst().orElse(null);
    }

    List<ChatKeysModel> getAbleByChatSdkId(Long chatSdkId);

    void removeByApiToken(String token);

    void flushCache();

}
