package com.hugai.modules.chat.controller;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.LockGroupConstant;
import com.hugai.common.constants.RedisCacheKey;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.session.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.core.session.entity.SessionDrawEditOpenaiCacheData;
import com.hugai.core.session.lock.SessionLockHandle;
import com.hugai.core.session.valid.SendDrawOpenAi;
import com.hugai.framework.log.annotation.Log;
import com.hugai.framework.sensitiveWord.annotation.SensitiveContentFilter;
import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;
import com.hugai.modules.chat.service.DrawOpenaiService;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.system.entity.vo.baseResource.ResourceOpenaiVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

    String CACHE_KEY = RedisCacheKey.LOCK_DRAW_OPENAI;

    String errorMessage = "绘图失败，游客用户或未配置openAi key的用户每%s小时可以请求%s次绘图接口，正在努力寻找白嫖或节约openai key的方案，谅解下\uD83D\uDE02\uD83D\uDE02";

    String errorMessageNoOpenDraw = "无有效的Openai Key。因token消耗大绘图接口暂时关闭了，可以在[个人中心]添加openai key后可畅玩本站功能~~~";

    private final DrawOpenaiService service;

    private final IBaseResourceConfigService resourceConfigService;

    private final IOpenaiKeysService openaiKeysService;

    private final RedisUtil redisUtil;

    @Log(title = "ai绘图消息发送（openai）")
    @SensitiveContentFilter(attrName = "prompt", resultType = SenWordFilterType.non)
    @ApiOperation(value = "ai绘图消息发送（openai）")
    @PostMapping("/sendAiDraw")
    public Result sendAiDraw(@RequestBody SessionDrawCreatedOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param, SendDrawOpenAi.class);

        param.setSize(param.getSizeWidth() + "x" + param.getSizeHeight());

        AtomicReference<ImageResult> imageResult = new AtomicReference<>();
        SessionLockHandle.init(LockGroupConstant.SESSION).handle(param.getSessionType(), param.getSessionId(), DrawType.OPENAI.getKey(), () -> {

            ResourceOpenaiVO resourceOpenai = resourceConfigService.getResourceOpenai();
            List<String> userAbleKeys = openaiKeysService.getUserAbleKeys();
            // 校验
            if (userAbleKeys.size() == 0 && Objects.nonNull(resourceOpenai.getOpenDraw()) && !resourceOpenai.getOpenDraw()) {
                throw new BusinessException(errorMessageNoOpenDraw);
            }

            String KEY = String.format(CACHE_KEY, param.getUserId());
            Integer count = redisUtil.getCacheObject(KEY);
            if (userAbleKeys.size() == 0 && Objects.nonNull(resourceOpenai.getDrawApiSendMax()) && (Objects.nonNull(count) && count >= resourceOpenai.getDrawApiSendMax())) {
                throw new BusinessException(String.format(errorMessage, resourceOpenai.getDrawApiCacheTime(), resourceOpenai.getDrawApiSendMax()));
            }
            imageResult.set(service.sendDrawCreatedOpenAi(param));

            redisUtil.incrBy(KEY, 1);
            redisUtil.expire(KEY, 1, TimeUnit.HOURS);
        }, "当前会话正在进行中，请等待结束");
        ImageResult result = imageResult.get();
        if (Objects.isNull(result)){
            return Result.fail();
        }
        return Result.success();
    }

    @Log(title = "ai绘图编辑图像（openai）")
    @SensitiveContentFilter(attrName = "prompt", resultType = SenWordFilterType.non)
    @ApiOperation(value = "ai绘图编辑图像（openai）")
    @PostMapping("/sendAiDrawEdit")
    public Result sendAiDrawEdit(@RequestBody SessionDrawEditOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param, SendDrawOpenAi.class);

        param.setSize(param.getSizeWidth() + "x" + param.getSizeHeight());
        OR.run(param.getResponseFormat(), StrUtil::isEmpty, item -> param.setResponseFormat("url"));

        AtomicReference<ImageResult> imageResult = new AtomicReference<>();
        SessionLockHandle.init(LockGroupConstant.SESSION).handle(param.getSessionType(), param.getSessionId(), DrawType.OPENAI.getKey(), () -> {
            ResourceOpenaiVO resourceOpenai = resourceConfigService.getResourceOpenai();
            List<String> userAbleKeys = openaiKeysService.getUserAbleKeys();
            // 校验
            if (userAbleKeys.size() == 0 && Objects.nonNull(resourceOpenai.getOpenDraw()) && !resourceOpenai.getOpenDraw()) {
                throw new BusinessException(errorMessageNoOpenDraw);
            }

            String KEY = String.format(CACHE_KEY, param.getUserId());
            Integer count = redisUtil.getCacheObject(KEY);
            if (userAbleKeys.size() == 0 && Objects.nonNull(resourceOpenai.getDrawApiSendMax()) && (Objects.nonNull(count) && count >= resourceOpenai.getDrawApiSendMax())) {
                throw new BusinessException(String.format(errorMessage, resourceOpenai.getDrawApiCacheTime(), resourceOpenai.getDrawApiSendMax()));
            }

            imageResult.set(service.sendDrawEditOpenAi(param));

            redisUtil.incrBy(KEY, 1);
            redisUtil.expire(KEY, 1, TimeUnit.HOURS);
        }, "当前会话正在进行中，请等待结束");
        ImageResult result = imageResult.get();
        if (Objects.isNull(result)){
            return Result.fail();
        }
        return Result.success();
    }

}
