package com.hugai.core.chat.account.strategy;

import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.chat.account.strategy.common.SdkAccountBuildCommon;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.hugai.modules.config.service.IChatSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WuHao
 * @since 2023/11/30 10:39
 */
@Service
public class OpenaiAccountBuilderStrategy extends SdkAccountBuildCommon {

    @Autowired
    public OpenaiAccountBuilderStrategy(BaseResourceWebApi baseResourceWebApi, IChatKeysService chatKeysService, IChatSdkHostService chatSdkHostService, IChatModelService chatModelService, IChatSdkService chatSdkService) {
        super(baseResourceWebApi, chatKeysService, chatSdkHostService, chatModelService, chatSdkService);
    }

    @Override
    public ChatSdkType type() {
        return ChatSdkType.openai;
    }


}
