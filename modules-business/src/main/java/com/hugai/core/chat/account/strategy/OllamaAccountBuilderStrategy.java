package com.hugai.core.chat.account.strategy;

import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.account.ClientParam;
import com.hugai.common.constants.Constants;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.config.model.ChatSdkHostModel;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.chat.account.strategy.common.SdkAccountBuildCommon;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.hugai.core.dataPool.DataRuleEnum;
import com.hugai.core.dataPool.factory.DataFactoryChatHost;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.hugai.modules.config.service.IChatSdkService;
import com.org.bebas.core.function.OR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author wuhao
 * @since 2024/5/26 3:35
 */
@Service
public class OllamaAccountBuilderStrategy extends SdkAccountBuildCommon {

    @Autowired
    public OllamaAccountBuilderStrategy(BaseResourceWebApi baseResourceWebApi, IChatKeysService chatKeysService, IChatSdkHostService chatSdkHostService, IChatModelService chatModelService, IChatSdkService chatSdkService) {
        super(baseResourceWebApi, chatKeysService, chatSdkHostService, chatModelService, chatSdkService);
    }

    @Override
    public ChatSdkType type() {
        return ChatSdkType.ollama;
    }

    @Override
    public ChatSdkAccount buildSdkAccount(ChatModelModel chatModel, ChatSdkModel chatSdk, ChatRequestParam chatRequestParam) {
        Long chatSdkId = chatSdk.getId();

        ResourceMainVO resourceMain = baseResourceWebApi.getResourceMain();
        List<ChatSdkHostModel> hostList = chatSdkHostService.getUsableByChatSdkId(chatSdkId);

        String rulesName = DataRuleEnum.getByName(chatSdk.getKeysRules()).name();

        ChatSdkAccount account = new ChatSdkAccount();
        account.setUserId(UserThreadLocal.getUserId());
        account.setSdkUniqueKey(this.type().getKey());
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

}
