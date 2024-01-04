package com.hugai.chatsdk.service;

import com.hugai.chatsdk.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.session.RecordData;
import com.hugai.common.enums.flow.ChatSdkType;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/11/28 9:58
 */
public interface ChatBusinessService<R> {

    /**
     * chat sdk 类型
     *
     * @return
     */
    ChatSdkType type();

    /**
     * 流式对话请求
     *
     * @param recordList
     * @param chatSdkAccount
     * @return
     */
    ChatSdkStorageResponse chatCompletionStream(List<RecordData> recordList, ChatSdkAccount chatSdkAccount);

    /**
     * 非流式对话
     *
     * @param recordList
     * @param chatSdkAccount
     * @return
     */
    ChatSdkStorageResponse chatCompletion(List<RecordData> recordList, ChatSdkAccount chatSdkAccount);

    /**
     * 转换为api请求参数
     *
     * @param recordList
     * @param account
     * @param requestConsumer
     * @return
     */
    R convertRequest(List<RecordData> recordList, ChatSdkAccount account, Consumer<R> requestConsumer);

}
