package com.hugai.modules.chat.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.LockGroupConstant;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.lock.SessionLockHandle;
import com.hugai.core.session.valid.Send;
import com.hugai.core.session.valid.SendDomain;
import com.hugai.core.sse.CacheSsePool;
import com.hugai.framework.log.annotation.Log;
import com.hugai.framework.sensitiveWord.annotation.SensitiveContentFilter;
import com.hugai.modules.chat.service.ChatService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/6/6 10:48
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.MODEL_API + "/chat")
@Api(tags = "聊天会话控制器")
public class ChatController {

    private final ChatService chatService;

    @Log(title = "聊天会话消息发送")
    @SensitiveContentFilter(attrName = "content")
    @ApiOperation(value = "聊天会话消息发送")
    @PostMapping("/send")
    public Result send(@RequestBody SessionCacheData param) {
        ValidatorUtil.validateEntity(param, Send.class);
        try {
            SessionLockHandle.init(LockGroupConstant.SESSION).handle(param.getSessionType(), param.getSessionId(), () -> {
                chatService.sendChatMessage(param);
            }, "当前会话正在进行中，请等待结束");
        } finally {
            String sseId = param.getSseId();
            OR.run(CacheSsePool.get(sseId), Objects::nonNull, SseEmitter::complete);
        }
        return Result.success();
    }

    @Log(title = "领域会话消息发送")
    @SensitiveContentFilter(attrName = "content")
    @ApiOperation(value = "领域会话消息发送")
    @PostMapping("/sendDomain")
    public Result sendDomain(@RequestBody SessionCacheData param) {
        ValidatorUtil.validateEntity(param, SendDomain.class);
        try {
            SessionLockHandle.init(LockGroupConstant.SESSION).handle(param.getSessionType(), param.getSessionId(), param.getDomainUniqueKey(), () -> {
                chatService.sendDomainMessage(param);
            }, "当前会话正在进行中，请等待结束");
        } finally {
            String sseId = param.getSseId();
            OR.run(CacheSsePool.get(sseId), Objects::nonNull, SseEmitter::complete);
        }
        return Result.success();
    }

}
