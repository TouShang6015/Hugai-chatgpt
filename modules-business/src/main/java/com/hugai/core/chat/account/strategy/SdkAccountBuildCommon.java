package com.hugai.core.chat.account.strategy;

import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.account.ClientParam;
import com.hugai.common.constants.Constants;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.config.model.ChatSdkHostModel;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.chat.account.service.SdkAccountBuildService;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.hugai.core.dataPool.DataRuleEnum;
import com.hugai.core.dataPool.factory.DataFactoryChatHost;
import com.hugai.core.dataPool.factory.DataFactoryChatKey;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.hugai.modules.config.service.IChatSdkService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/11/30 10:38
 */
public abstract class SdkAccountBuildCommon implements SdkAccountBuildService {

    @Override
    public ChatSdkAccount buildSdkAccount(ChatModelModel chatModel, ChatSdkModel chatSdk, ChatRequestParam chatRequestParam) {
        Long chatSdkId = chatSdk.getId();

        ResourceMainVO resourceMain = SpringUtils.getBean(BaseResourceWebApi.class).getResourceMain();
        List<ChatKeysModel> chatKeys = SpringUtils.getBean(IChatKeysService.class).getAbleByChatSdkId(chatSdkId);
        List<ChatSdkHostModel> hostList = SpringUtils.getBean(IChatSdkHostService.class).getUsableByChatSdkId(chatSdkId);

        String rulesName = DataRuleEnum.getByName(chatSdk.getKeysRules()).name();
        ChatKeysModel chatKeysModel = DataFactoryChatKey.poolGetKey(rulesName, chatKeys);

        ChatSdkAccount account = new ChatSdkAccount();
        account.setSdkUniqueKey(this.type().getKey());
        account.setApiToken(chatKeysModel.getApiToken());
        account.setAppId(chatKeysModel.getAppId());
        OR.run(chatModel, Objects::nonNull, e -> {
            account.setModelValue(e.getModelValue());
            account.setOnceToken(e.getOnceToken());
        });
        OR.run(chatRequestParam, Objects::nonNull, e -> {
            account.setConnectId(e.getConnectId());
            account.setExtendParam(e.getExtendParam());
        });

        // 客户端配置
        ChatSdkHostModel chatSdkHostModel = DataFactoryChatHost.poolGetKey(rulesName, hostList);
        ClientParam clientParam = new ClientParam();
        clientParam.setBaseUrl(chatSdkHostModel.getHostUrl());
        clientParam.setIfProxy(Constants.BOOLEAN.TRUE.equals(chatSdkHostModel.getIfProxy()));
        clientParam.setProxyHost(resourceMain.getProxyHost());
        clientParam.setProxyPort(resourceMain.getProxyPort());
        clientParam.setTimeoutValue(chatSdkHostModel.getTimeoutValue());
        clientParam.setMaxConnect(chatSdkHostModel.getMaxConnect());

        account.setClientParam(clientParam);
        return account;
    }

    @Override
    public ChatSdkAccount buildSdkAccount(String modelUnique) {
        ChatModelModel chatModel = SpringUtils.getBean(IChatModelService.class).getByUniqueKey(modelUnique);

        ChatSdkModel chatSdkModel = SpringUtils.getBean(IChatSdkService.class).getById(chatModel.getChatSdkId());

        return this.buildSdkAccount(chatModel, chatSdkModel, null);
    }

    @Override
    public ChatSdkAccount buildSdkAccountBySdkUnique(String sdkUnique) {

        ChatSdkModel chatSdkModel = SpringUtils.getBean(IChatSdkService.class).getByUniqueKey(sdkUnique);

        return this.buildSdkAccount(null, chatSdkModel, null);
    }
}
