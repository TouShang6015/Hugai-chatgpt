package com.hugai.core.openai.service;

import com.hugai.common.enums.StreamResponseTypeEnum;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

// todo 2023年8月22日 17:47:02 待办
/**
 * openai 响应结果消息推送
 *
 * @author WuHao
 * @since 2023/8/22 17:34
 */
@Slf4j
public class MessageSendService {

    private final String connectId;

    private StreamResponseTypeEnum streamResponseType;

    private AtomicBoolean running = new AtomicBoolean(true);

    private Queue<String> messageQueue = new ConcurrentLinkedQueue<>();

    public MessageSendService(String connectId) {
        this.connectId = connectId;
        ResourceMainVO resourceMain = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceMain();
        this.streamResponseType = StreamResponseTypeEnum.getDefaultType(resourceMain.getStreamResponseType());
        // 执行自旋
        this.queueSpin();
    }

    private void queueSpin(){
    }

    public void close(){
        this.running.set(false);
    }

}
