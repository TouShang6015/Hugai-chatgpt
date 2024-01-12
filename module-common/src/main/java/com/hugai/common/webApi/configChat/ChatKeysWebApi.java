package com.hugai.common.webApi.configChat;

import com.hugai.common.modules.entity.config.model.ChatKeysModel;

/**
 * @author WuHao
 * @since 2024/1/12 10:01
 */
public interface ChatKeysWebApi {

    ChatKeysModel queryByApiToken(String apiToken);

}
