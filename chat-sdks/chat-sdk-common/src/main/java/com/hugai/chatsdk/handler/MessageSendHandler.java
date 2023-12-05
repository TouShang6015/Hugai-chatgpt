package com.hugai.chatsdk.handler;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.enums.StreamResponseTypeEnum;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.chatsdk.handler.pool.SessionMessageSendPool;
import com.hugai.chatsdk.utils.ContentUtil;
import com.hugai.common.pool.CacheSsePool;
import com.hugai.common.pool.ChatSocketPool;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 响应结果消息推送
 *
 * @author WuHao
 * @since 2023/8/22 17:34
 */
@Slf4j
public class MessageSendHandler {

    private final String connectId;

    private StreamResponseTypeEnum streamResponseType;

    private AtomicBoolean running = new AtomicBoolean(true);

    private Queue<String> messageQueue = new ConcurrentLinkedQueue<>();

    private SseEmitter sseEmitter;

    private Session session;

    private AtomicBoolean stopState = new AtomicBoolean(false);

    /**
     * @param connectId
     */
    public MessageSendHandler(String connectId) {
        this.connectId = connectId;
        if (StrUtil.isEmpty(connectId))
            return;
        ResourceMainVO resourceMain = SpringUtils.getBean(BaseResourceWebApi.class).getResourceMain();
        this.streamResponseType = StreamResponseTypeEnum.getDefaultType(resourceMain.getStreamResponseType());
        log.info("[MessageSendHandler] 消息推送 流式响应模式：{}", this.streamResponseType.name());
        this.sseEmitter = CacheSsePool.get(this.connectId);
        this.session = ChatSocketPool.get(this.connectId);
        // 存放缓存池
        SessionMessageSendPool.add(connectId, this);
        // 执行自旋
        this.queueSpin();
    }

    private void queueSpin() {
        if (StrUtil.isEmpty(connectId))
            return;
        if (this.streamResponseType.equals(StreamResponseTypeEnum.Websocket)) {
            ThreadPoolTaskExecutor taskExecutor = SpringUtils.getBean(ThreadPoolTaskExecutor.class);
            OR.run(this.session, Objects::nonNull, session -> {
                taskExecutor.execute(() -> {
                    log.info("connectId:{} - [延时队列] 执行", connectId);
                    while (running.get() && session.isOpen()) {
                        try {
                            StringBuilder message = new StringBuilder();
                            for (String ignore : messageQueue) {
                                message.append(messageQueue.poll());
                            }
                            if (StrUtil.isNotEmpty(message)) {
                                session.getBasicRemote().sendText(message.toString());
                            }
                            Thread.sleep(100);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("connectId:{} - [延时队列] 释放", connectId);
                });
            });
        }
    }

    /**
     * 队列添加内容
     *
     * @param content
     */
    public void queueAdd(String content) {
        if (StrUtil.isEmpty(connectId))
            return;
        if (stopState.get()) {
            throw new BusinessException(null, HttpStatus.SUCCESS);
        }
        this.messageQueue.add(content);
        if (this.streamResponseType.equals(StreamResponseTypeEnum.SSE)) {
            String poll = this.messageQueue.poll();
            OR.run(this.sseEmitter, Objects::nonNull, sse -> {
                try {
                    sse.send(SseEmitter.event().data(ContentUtil.convertNormal(poll)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void close() {
        if (StrUtil.isEmpty(connectId))
            return;
        OR.run(this.sseEmitter, Objects::nonNull, () -> CacheSsePool.remove(this.connectId));
        OR.run(this.session, Objects::nonNull, () -> {
            try {
                Thread.sleep(110);
                this.running.set(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ChatSocketPool.remove(this.connectId);
        });
        // 释放资源
        SessionMessageSendPool.remove(this.connectId);
    }

    public void stop() {
        if (StrUtil.isEmpty(connectId))
            return;
        this.stopState.set(true);
    }

}
