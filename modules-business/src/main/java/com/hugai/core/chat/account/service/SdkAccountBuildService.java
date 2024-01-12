package com.hugai.core.chat.account.service;

import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.core.chat.entity.ChatRequestParam;

/**
 * @author WuHao
 * @since 2023/11/30 10:32
 */
public interface SdkAccountBuildService{

    /**
     * sdk 标识
     *
     * @return
     */
    ChatSdkType type();

    /**
     * 构建sdk账户参数
     *
     * @param chatModel
     * @param chatSdk
     * @param chatRequestParam
     * @return
     */
    ChatSdkAccount buildSdkAccount(ChatModelModel chatModel, ChatSdkModel chatSdk, ChatRequestParam chatRequestParam);


    ChatSdkAccount buildSdkAccount(String modelUnique);

    ChatSdkAccount buildSdkAccountBySdkUnique(String sdkUnique);

}
