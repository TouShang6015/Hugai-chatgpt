package com.hugai.chatsdk.spark.socket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.handler.MessageSendHandler;
import com.hugai.chatsdk.spark.entity.request.ChatRequest;
import com.hugai.chatsdk.spark.entity.response.ChatResponse;
import com.hugai.chatsdk.spark.entity.response.Header;
import com.org.bebas.core.function.OR;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * 对话Websocket连接
 *
 * @author WuHao
 * @since 2023/12/27 13:35
 */
@Slf4j
public class SparkChatSocketListener extends WebSocketListener {

    public WebSocket webSocket;
    public CountDownLatch latch;

    @Getter
    private ChatSdkStorageResponse chatSdkStorageResponse;
    @Getter
    private ChatRequest chatRequest;
    @Getter
    private MessageSendHandler messageSendHandler;

    private RecordData recordData;

    public SparkChatSocketListener(ChatRequest chatRequest, MessageSendHandler messageSendHandler, ChatSdkStorageResponse chatSdkStorageResponse) {
        this.chatRequest = chatRequest;
        this.messageSendHandler = messageSendHandler;
        this.latch = new CountDownLatch(1);
        this.chatSdkStorageResponse = chatSdkStorageResponse;
        this.recordData = chatSdkStorageResponse.pushData(new RecordData());
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
        this.webSocket.send(this.chatRequest.toString());
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        ChatResponse chatResponse = JSON.parseObject(text, ChatResponse.class);
        Header header = chatResponse.getHeader();

        if (header.getCode() != 0) {
            log.error("[平台 - 讯飞星火] socket onMessage 异常，code: {} | sid: {}", header.getCode(), header.getSid());
            this.close();
        }

        OR.run(chatResponse.getPayload().getChoices().getText(), CollUtil::isNotEmpty, textList -> {
            textList.forEach(res -> {
                OR.run(res.getRole(), StrUtil::isNotEmpty, recordData::setRole);
                String resContent = Optional.ofNullable(res.getContent()).orElse("");
                this.recordData.getContentSB().append(resContent);
                // 发送消息
                this.messageSendHandler.queueAdd(resContent);
            });
        });

        // 已结束
        if (header.getStatus() == 2) {
//            log.debug("[平台 - 讯飞星火] 流式响应完成：{}",text);
            recordData.setContent(recordData.getContentSB().toString());
            this.latch.countDown();
        }

    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        this.latch.countDown();
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        this.latch.countDown();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        t.printStackTrace();
        this.latch.countDown();
        super.onFailure(webSocket, t, response);
        try {
            if (null != response) {
                int code = response.code();
                log.error("[平台 - 讯飞星火] socket onFailure 异常，code: {} | body: {}", code, response.body().string());
                if (101 != code) {
                    log.error("[平台 - 讯飞星火] socket onFailure 连接异常");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     */
    public void close() {
        log.info("[平台 - 讯飞星火] socket 关闭连接");
        this.webSocket.close(1000, "");
        this.messageSendHandler.close();
    }

}
