package com.hugai.core.chat.account.strategy.common;

import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.account.ClientParam;
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
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.hugai.modules.config.service.IChatSdkService;
import com.org.bebas.core.function.OR;

import java.util.List;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/11/30 10:38
 */
public abstract class SdkAccountBuildCommon implements SdkAccountBuildService {

    protected BaseResourceWebApi baseResourceWebApi;
    protected IChatKeysService chatKeysService;
    protected IChatSdkHostService chatSdkHostService;
    protected IChatModelService chatModelService;
    protected IChatSdkService chatSdkService;

    public SdkAccountBuildCommon(BaseResourceWebApi baseResourceWebApi, IChatKeysService chatKeysService, IChatSdkHostService chatSdkHostService, IChatModelService chatModelService, IChatSdkService chatSdkService) {
        this.baseResourceWebApi = baseResourceWebApi;
        this.chatKeysService = chatKeysService;
        this.chatSdkHostService = chatSdkHostService;
        this.chatModelService = chatModelService;
        this.chatSdkService = chatSdkService;
    }

    @Override
    public ChatSdkAccount buildSdkAccount(ChatModelModel chatModel, ChatSdkModel chatSdk, ChatRequestParam chatRequestParam) {
        Long chatSdkId = chatSdk.getId();

        ResourceMainVO resourceMain = baseResourceWebApi.getResourceMain();
        List<ChatKeysModel> chatKeys = chatKeysService.getAbleByChatSdkId(chatSdkId);
        List<ChatSdkHostModel> hostList = chatSdkHostService.getUsableByChatSdkId(chatSdkId);

        String rulesName = DataRuleEnum.getByName(chatSdk.getKeysRules()).name();
        ChatKeysModel chatKeysModel = DataFactoryChatKey.poolGetKey(rulesName, chatKeys);

        ChatSdkAccount account = new ChatSdkAccount();
        account.setUserId(UserThreadLocal.getUserId());
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
            account.setChatRequestParamJson(JSON.toJSONString(e));
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
        ChatModelModel chatModel = chatModelService.getByUniqueKey(modelUnique);

        ChatSdkModel chatSdkModel = chatSdkService.getById(chatModel.getChatSdkId());

        return this.buildSdkAccount(chatModel, chatSdkModel, null);
    }

    @Override
    public ChatSdkAccount buildSdkAccountBySdkUnique(String sdkUnique) {

        ChatSdkModel chatSdkModel = chatSdkService.getByUniqueKey(sdkUnique);

        return this.buildSdkAccount(null, chatSdkModel, null);
    }
}
