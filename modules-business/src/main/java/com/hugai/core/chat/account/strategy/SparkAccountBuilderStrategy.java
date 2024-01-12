package com.hugai.core.chat.account.strategy;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.account.ClientParam;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.chat.account.strategy.common.SdkAccountBuildCommon;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.hugai.core.dataPool.DataRuleEnum;
import com.hugai.core.dataPool.factory.DataFactoryChatKey;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.hugai.modules.config.service.IChatSdkService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/11/30 10:39
 */
@Service
public class SparkAccountBuilderStrategy extends SdkAccountBuildCommon {

    @Autowired
    public SparkAccountBuilderStrategy(BaseResourceWebApi baseResourceWebApi, IChatKeysService chatKeysService, IChatSdkHostService chatSdkHostService, IChatModelService chatModelService, IChatSdkService chatSdkService) {
        super(baseResourceWebApi, chatKeysService, chatSdkHostService, chatModelService, chatSdkService);
    }

    @Override
    public ChatSdkType type() {
        return ChatSdkType.spark;
    }

    @Override
    public ChatSdkAccount buildSdkAccount(ChatModelModel chatModel, ChatSdkModel chatSdk, ChatRequestParam chatRequestParam) {
        Assert.notNull(chatModel, () -> new BusinessException("构建对话参数失败，chatMode不能为空"));
        Long chatSdkId = chatSdk.getId();

        ResourceMainVO resourceMain = baseResourceWebApi.getResourceMain();
        List<ChatKeysModel> chatKeys = chatKeysService.getAbleByChatSdkId(chatSdkId);

        String rulesName = DataRuleEnum.getByName(chatSdk.getKeysRules()).name();
        ChatKeysModel chatKeysModel = DataFactoryChatKey.poolGetKey(rulesName, chatKeys);

        ChatSdkAccount account = new ChatSdkAccount();
        account.setUserId(UserThreadLocal.getUserId());
        account.setSdkUniqueKey(this.type().getKey());
        account.setApiToken(chatKeysModel.getApiToken());
        account.setAppId(chatKeysModel.getAppId());
        account.setApiSecret(chatKeysModel.getApiSecret());
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
        ClientParam clientParam = new ClientParam();
        clientParam.setBaseUrl(chatModel.getRequestUrl());
        clientParam.setIfProxy(false);
        clientParam.setProxyHost(resourceMain.getProxyHost());
        clientParam.setProxyPort(resourceMain.getProxyPort());

        account.setClientParam(clientParam);
        return account;
    }
}
