package com.hugai.core.drawTask.entity;

import com.hugai.chatsdk.common.context.ChatBusinessServiceContext;
import com.hugai.core.chat.account.SdkAccountBuildContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WuHao
 * @since 2023/12/4 22:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CacheService {

    private SdkAccountBuildContext sdkAccountBuildContext;

    private ChatBusinessServiceContext chatBusinessServiceContext;

}
