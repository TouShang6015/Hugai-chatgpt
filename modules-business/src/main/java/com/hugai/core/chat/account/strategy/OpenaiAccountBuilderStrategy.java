package com.hugai.core.chat.account.strategy;

import com.hugai.common.enums.flow.ChatSdkType;
import org.springframework.stereotype.Service;

/**
 * @author WuHao
 * @since 2023/11/30 10:39
 */
@Service
public class OpenaiAccountBuilderStrategy extends SdkAccountBuildCommon {

    @Override
    public ChatSdkType type() {
        return ChatSdkType.openai;
    }

}
