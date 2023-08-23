package com.hugai.core.openai.service;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.enums.StreamResponseTypeEnum;
import com.hugai.core.openai.utils.ContentUtil;
import com.hugai.core.sse.CacheSsePool;
import com.hugai.core.websocket.pool.ChatSocketPool;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
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
 * openai 响应结果消息推送
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

    public MessageSendHandler(String connectId) {
        this.connectId = connectId;
        ResourceMainVO resourceMain = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceMain();
        this.streamResponseType = StreamResponseTypeEnum.getDefaultType(resourceMain.getStreamResponseType());
        log.info("[MessageSendHandler] 消息推送 流式响应模式：{}",this.streamResponseType.name());
        this.sseEmitter = CacheSsePool.get(this.connectId);
        this.session = ChatSocketPool.get(this.connectId);
        // 执行自旋
        this.queueSpin();
    }

    private void queueSpin() {
        ThreadPoolTaskExecutor taskExecutor = SpringUtils.getBean(ThreadPoolTaskExecutor.class);
        if (this.streamResponseType.equals(StreamResponseTypeEnum.Websocket)) {
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
                                session.getAsyncRemote().sendText(message.toString());
                            }
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
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
        this.running.set(false);
        OR.run(this.sseEmitter,Objects::nonNull,() -> CacheSsePool.remove(this.connectId));
        OR.run(this.session,Objects::nonNull,() -> ChatSocketPool.remove(this.connectId));
    }

}
