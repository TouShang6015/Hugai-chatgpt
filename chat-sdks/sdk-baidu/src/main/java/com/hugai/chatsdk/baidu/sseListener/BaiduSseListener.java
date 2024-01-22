package com.hugai.chatsdk.baidu.sseListener;

import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.baidu.entity.response.ChatResponse;
import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.handler.MessageSendHandler;
import com.hugai.common.enums.ChatRole;
import com.org.bebas.core.function.OR;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 百度千帆sse监听
 *
 * @author WuHao
 * @since 2024/1/12 14:08
 */
@Slf4j
public class BaiduSseListener extends EventSourceListener {

    public CountDownLatch latch;
    public EventSource eventSource;

    private MessageSendHandler messageSendHandler;
    private RecordData recordData;
    @Getter
    private ChatSdkStorageResponse chatSdkStorageResponse;

    public BaiduSseListener(MessageSendHandler messageSendHandler) {
        this.messageSendHandler = messageSendHandler;
        this.latch = new CountDownLatch(1);
        this.chatSdkStorageResponse = new ChatSdkStorageResponse();
        this.recordData = this.chatSdkStorageResponse.pushData(new RecordData());
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        super.onOpen(eventSource, response);
        this.eventSource = eventSource;
    }

    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        log.info("[百度千帆SSE响应]：{}", data);
        ChatResponse chatResponse = JSON.parseObject(data, ChatResponse.class);
        this.recordData.setRole(ChatRole.assistant.name());
        String resContent = chatResponse.getResult();
        this.recordData.getContentSB().append(resContent);
        this.messageSendHandler.queueAdd(resContent);
        if (chatResponse.getIs_end()) {
            this.recordData.setContent(this.recordData.getContentSB().toString());
            this.latch.countDown();
        }
    }

    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        super.onFailure(eventSource, t, response);
        this.latch.countDown();
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        super.onClosed(eventSource);
        this.latch.countDown();
    }

    public void close() {
        OR.run(this.eventSource, Objects::nonNull, EventSource::cancel);
        OR.run(this.messageSendHandler, Objects::nonNull, MessageSendHandler::close);
    }

}
