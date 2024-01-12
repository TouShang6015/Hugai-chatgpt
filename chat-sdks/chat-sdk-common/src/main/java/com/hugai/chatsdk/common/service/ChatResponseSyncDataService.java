package com.hugai.chatsdk.common.service;

import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.session.RecordData;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/12/27 8:52
 */
public interface ChatResponseSyncDataService {

    /**
     * 同步持久化对话响应数据
     *
     * @param response
     * @param list
     */
    void syncChatResponse(ChatSdkAccount requestParam, ChatSdkStorageResponse response, List<RecordData> list);

}
