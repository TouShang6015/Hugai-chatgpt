package com.hugai.modules.chat.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.LockGroupConstant;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.lock.SessionLockHandle;
import com.hugai.core.session.valid.Send;
import com.hugai.core.session.valid.SendDomain;
import com.hugai.framework.log.annotation.Log;
import com.hugai.framework.sensitiveWord.annotation.SensitiveContentFilter;
import com.hugai.modules.chat.service.ChatService;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final String lockErrorMessage = "当前会话正在进行中，请等待结束";

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Log(title = "聊天会话消息发送")
    @SensitiveContentFilter(attrName = "content")
    @ApiOperation(value = "聊天会话消息发送")
    @PostMapping("/send")
    public Result send(@RequestBody SessionCacheData param) {
        ValidatorUtil.validateEntity(param, Send.class);
        param.setUserId(SecurityContextUtil.getUserId());

        SessionLockHandle sessionLockHandle = SessionLockHandle.init(LockGroupConstant.SESSION);
        RLock lock = sessionLockHandle.getLock(param.getSessionType(), param.getSessionId());
        if (lock.isLocked()) {
            throw new BusinessException(lockErrorMessage);
        }

        taskExecutor.execute(() -> {
            try {
                UserThreadLocal.set(param.getUserId());
                sessionLockHandle.handle(param.getSessionType(), param.getSessionId(), () -> {
                    chatService.sendChatMessage(param);
                }, lockErrorMessage);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                UserThreadLocal.remove();
            }
        });

        return Result.success();
    }

    @Log(title = "领域会话消息发送")
    @SensitiveContentFilter(attrName = "content")
    @ApiOperation(value = "领域会话消息发送")
    @PostMapping("/sendDomain")
    public Result sendDomain(@RequestBody SessionCacheData param) {
        ValidatorUtil.validateEntity(param, SendDomain.class);
        param.setUserId(SecurityContextUtil.getUserId());

        SessionLockHandle sessionLockHandle = SessionLockHandle.init(LockGroupConstant.SESSION);
        RLock lock = sessionLockHandle.getLock(param.getSessionType(), param.getSessionId(), param.getDomainUniqueKey());
        if (lock.isLocked()) {
            throw new BusinessException(lockErrorMessage);
        }

        taskExecutor.execute(() -> {
            try {
                UserThreadLocal.set(param.getUserId());
                sessionLockHandle.handle(param.getSessionType(), param.getSessionId(), param.getDomainUniqueKey(), () -> {
                    chatService.sendDomainMessage(param);
                }, lockErrorMessage);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                UserThreadLocal.remove();
            }
        });

        return Result.success();
    }

}
