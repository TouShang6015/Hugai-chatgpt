package com.hugai.modules.chat.controller;

import cn.hutool.core.lang.UUID;
import com.hugai.core.sse.CacheSsePool;
import com.org.bebas.utils.uuid.SFIDWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author WuHao
 * @since 2023/7/5 14:24
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sse")
@Api(tags = "sse通用请求控制器")
public class SseController {

    @GetMapping("/connect")
    @ApiOperation(value = "获取会话连接")
    public SseEmitter connect() {
        SseEmitter sse = new SseEmitter(0L);

        String sseId = String.valueOf(SFIDWorker.nextId());

        try {
            // 退出sse回调
            sse.onCompletion(() -> {
                CacheSsePool.remove(sseId);
                log.info("sseId：[{}]断开连接", sseId);
            });
            sse.send(SseEmitter.event().id(sseId).data(sseId).reconnectTime(5000));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("sse连接失败");
        }
        // 存缓存
        CacheSsePool.add(sseId, sse);
        return sse;
    }

}
