package com.hugai.common.support.sms.strategy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.entity.baseResource.ResourceSmsConfigVO;
import com.hugai.common.modules.entity.system.model.SysLogSmsModel;
import com.hugai.common.support.sms.entity.SmsBaseParam;
import com.hugai.common.support.sms.enums.SmsCodeEnum;
import com.hugai.common.support.sms.enums.SmsTypeEnum;
import com.hugai.common.support.sms.service.SmsSendService;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.common.webApi.log.SysLogSmsWebApi;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author WuHao
 * @since 2023/12/7 10:18
 */
public abstract class SendServiceCommon implements SmsSendService {

    protected final Logger log = LoggerFactory.getLogger(SendServiceCommon.class);

    protected RedisUtil redisUtils;

    protected HashOperations<String, Object, SmsBaseParam> hashOperations;

    protected SysLogSmsWebApi logSmsWebApi;

    protected BaseResourceWebApi resourceConfigWebApi;

    /**
     * 缓存过期时间 秒
     */
    private final Integer CODE_CACHE_EXPIRE = 60 * 15;

    public SendServiceCommon(RedisUtil redisUtils, HashOperations<String, Object, SmsBaseParam> hashOperations, SysLogSmsWebApi logSmsWebApi, BaseResourceWebApi resourceConfigWebApi) {
        this.redisUtils = redisUtils;
        this.hashOperations = hashOperations;
        this.logSmsWebApi = logSmsWebApi;
        this.resourceConfigWebApi = resourceConfigWebApi;
    }

    protected abstract void sendExecute(SmsBaseParam param);

    @Override
    public String sendSmsCode(SmsTypeEnum smsVerifyEnum, String uniqueKey, String content) {
        // 参数校验
        Assert.notNull(smsVerifyEnum, () -> new BusinessException("验证码类型不能为空"));
        Assert.notNull(smsVerifyEnum, () -> new BusinessException("验证码唯一键不能为空"));

        ResourceSmsConfigVO resourceSmsConfig = resourceConfigWebApi.getResourceSmsConfig();

        // 验证短信次数
        Assert.isFalse(this.countSendCache(uniqueKey) >= resourceSmsConfig.getCodeLimitCount(), () -> new BusinessException("验证码发送频繁，请稍后重试"));

        // 短信实体
        SmsBaseParam smsParam = this.getEntity(smsVerifyEnum, uniqueKey);
        if (Objects.isNull(smsParam)) {
            smsParam = new SmsBaseParam();
        }


        // 更新每天的短信次数
        String createTimeFormat = DateUtil.format(smsParam.getCreateTime(), "yyyyMMdd");
        String nowTimeFormat = DateUtil.format(new Date(), "yyyyMMdd");
        if (!createTimeFormat.equals(nowTimeFormat)) {
            smsParam.setCount(NumberUtils.INTEGER_ZERO);
        }
        // code 码
        String code = getCode(smsParam);
        smsParam.setCode(code);
        smsParam.setCount(smsParam.getCount() + 1);
        smsParam.setStatus(Boolean.FALSE);
        smsParam.setType(smsVerifyEnum.name());
        smsParam.setUniqueKey(uniqueKey);
        smsParam.setExpireTime(DateUtils.addSeconds(new Date(), resourceSmsConfig.getCodeEffectiveTime()));


        final String finalContent = StrUtil.isEmpty(content) ? code : String.format(content, code);

        // todo 发送验证码
        this.sendExecute(smsParam);
        // 加日志
        logSmsWebApi.saveLog(
                SysLogSmsModel.builder()
                        .smsType(smsVerifyEnum.name())
                        .smsTypeDescription(smsVerifyEnum.getDescription())
                        .content(finalContent)
                        .uniqueKey(uniqueKey)
                        .build()
        );

        // redis存储短信实体
        this.updateEntity(smsVerifyEnum, uniqueKey, smsParam);
        String countKey = this.getCacheKey(uniqueKey) + "COUNT";
        if (!redisUtils.redisTemplate.hasKey(countKey)) {
            redisUtils.setCacheObject(countKey, 1, Long.valueOf(resourceSmsConfig.getCodeLimitTime()), TimeUnit.SECONDS);
        } else {
            redisUtils.incr(countKey);
        }
        return code;
    }

    @Override
    public SmsCodeEnum verifyCode(SmsTypeEnum smsVerifyEnum, String uniqueKey, String code) {
        if (StringUtils.isEmpty(code)) {
            return SmsCodeEnum.CODE_NOT_MATCHING;      // 验证码不正确
        }
        // 短信实体
        SmsBaseParam smsParam = this.getEntity(smsVerifyEnum, uniqueKey);
        if (Objects.isNull(smsParam)) {
            return SmsCodeEnum.NOT_SEND_CODE;      // 未发送短信验证码
        }
        if (!smsParam.getCode().equals(code)) {
            return SmsCodeEnum.CODE_NOT_MATCHING;  // 验证码不正确
        }
        Date expireTime = smsParam.getExpireTime();
        if (Instant.now().toEpochMilli() > expireTime.getTime()) {
            return SmsCodeEnum.PAST_DUE;       // 验证码已过期
        }
        if (smsParam.getStatus()) {
            return SmsCodeEnum.ALREADY_USE;    // 验证码已被使用
        }

        return SmsCodeEnum.SUCCESS;
    }

    @Override
    public SmsBaseParam getEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey) {
        String key = this.getCacheKey(uniqueKey);
        return hashOperations.get(key, smsVerifyEnum.name());
    }

    @Override
    public void updateEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey, SmsBaseParam smsParam) {
        String key = this.getCacheKey(uniqueKey);
        hashOperations.put(key, smsVerifyEnum.name(), smsParam);
    }

    @Override
    public int countSendCache(String uniqueKey) {
        String countKey = this.getCacheKey(uniqueKey) + "COUNT";
        return (int) Optional.ofNullable(redisUtils.getCacheObject(countKey)).orElse(0);
    }

    protected String getCacheKey(String uniqueKey) {
        return "SMS:" + this.type().name() + ":" + uniqueKey + ":";
//        return "SMS:" + uniqueKey + ":";
    }

    /**
     * 获取验证码
     *
     * @param smsEntity
     * @return
     */
    protected String getCode(SmsBaseParam smsEntity) {
        if (StrUtil.isNotEmpty(smsEntity.getCode())
                && smsEntity.getStatus().equals(Boolean.FALSE)
                && Objects.nonNull(smsEntity.getExpireTime())
                && Instant.now().toEpochMilli() < smsEntity.getExpireTime().getTime()) {
            return smsEntity.getCode();
        }
        // 新的code码
        return (Math.random() + "").substring(2, 2 + 6);
    }

    protected String buildSendCodeText(String code) {
        if (StrUtil.isEmpty(code))
            return null;
        ResourceSmsConfigVO resourceSmsConfig = resourceConfigWebApi.getResourceSmsConfig();
        String textSendCode = resourceSmsConfig.getTextSendCode();
        if (StrUtil.isEmpty(textSendCode))
            return null;
        Integer codeEffectiveTime = resourceSmsConfig.getCodeEffectiveTime();
        if (Objects.isNull(codeEffectiveTime))
            return null;
        Map<String, Object> map = MapUtil.<String, Object>builder()
                .put("code", code)
                .put("time", codeEffectiveTime / 60)
                .build();
        return StrFormatter.format(textSendCode, map, false);
    }

}
