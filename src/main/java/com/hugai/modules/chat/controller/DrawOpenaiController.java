package com.hugai.modules.chat.controller;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.LockGroupConstant;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.session.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.core.session.entity.SessionDrawEditOpenaiCacheData;
import com.hugai.core.session.lock.SessionLockHandle;
import com.hugai.core.session.valid.SendDrawOpenAi;
import com.hugai.framework.sensitiveWord.annotation.SensitiveContentFilter;
import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;
import com.hugai.modules.chat.service.DrawOpenaiService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.utils.result.Result;
import com.theokanning.openai.image.ImageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author WuHao
 * @since 2023/7/17 9:35
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.MODEL_API + "/draw/openai")
@Api(tags = "绘画控制器（openai）")
public class DrawOpenaiController {

    private final DrawOpenaiService service;

    @SensitiveContentFilter(attrName = "prompt",resultType = SenWordFilterType.non)
    @ApiOperation(value = "ai绘图消息发送（openai）")
    @PostMapping("/sendAiDraw")
    public Result sendAiDraw(@RequestBody SessionDrawCreatedOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param, SendDrawOpenAi.class);

        OR.run(param.getSize(), StrUtil::isNotEmpty,size -> param.setSize(size + "x" + size));

        AtomicReference<ImageResult> imageResult = new AtomicReference<>();
        SessionLockHandle.init(LockGroupConstant.SESSION).handle(param.getSessionType(), param.getSessionId(), DrawType.OPENAI.getKey(), () -> {
            imageResult.set(service.sendDrawCreatedOpenAi(param));
        }, "当前会话正在进行中，请等待结束");
        return Result.success(imageResult.get());
    }

    @SensitiveContentFilter(attrName = "prompt",resultType = SenWordFilterType.non)
    @ApiOperation(value = "ai绘图编辑图像（openai）")
    @PostMapping("/sendAiDrawEdit")
    public Result sendAiDrawEdit(@RequestBody SessionDrawEditOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param, SendDrawOpenAi.class);

        OR.run(param.getSize(), StrUtil::isNotEmpty,size -> param.setSize(size + "x" + size));
        OR.run(param.getResponseFormat(), StrUtil::isEmpty,item -> param.setResponseFormat("url"));

        AtomicReference<ImageResult> imageResult = new AtomicReference<>();
        SessionLockHandle.init(LockGroupConstant.SESSION).handle(param.getSessionType(), param.getSessionId(), DrawType.OPENAI.getKey(), () -> {
            imageResult.set(service.sendDrawEditOpenAi(param));
        }, "当前会话正在进行中，请等待结束");
        return Result.success(imageResult.get());
    }

}
