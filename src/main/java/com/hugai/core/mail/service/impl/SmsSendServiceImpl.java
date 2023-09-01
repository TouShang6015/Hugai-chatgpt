package com.hugai.core.mail.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.MQConstants;
import com.hugai.core.mail.entity.MailRequest;
import com.hugai.core.mail.entity.SmsBaseParam;
import com.hugai.core.mail.enums.SmsCodeEnum;
import com.hugai.core.mail.enums.SmsTypeEnum;
import com.hugai.core.mail.service.SmsSendService;
import com.hugai.framework.asyncMessage.MessageService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author wyj
 * @date 2022/10/19 9:45
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Resource
    private RedisUtil redisUtils;
    @Resource
    private HashOperations<String, Object, SmsBaseParam> hashOperations;
    @Resource
    private MessageService messageService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 缓存过期时间 秒
     */
    private final Integer CODE_CACHE_EXPIRE = 60 * 15;

    /**
     * 短信验证码发送
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @return
     */
    @Override
    public String sendSmsCode(SmsTypeEnum smsVerifyEnum, String uniqueKey, String content) {
        // 参数校验
        Assert.notNull(smsVerifyEnum, () -> new BusinessException("验证码类型不能为空"));
        Assert.notNull(smsVerifyEnum, () -> new BusinessException("验证码唯一键不能为空"));
        // 验证短信次数，每分钟最多发3个短信
        Assert.isFalse(this.countSendCache(uniqueKey) >= 3, () -> new BusinessException("验证码发送频繁，请稍后重试"));

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
        smsParam.setValue(code);
        smsParam.setCount(smsParam.getCount() + 1);
        smsParam.setStatus(Boolean.FALSE);
        smsParam.setType(smsVerifyEnum.name());
        smsParam.setUniqueKey(uniqueKey);
        smsParam.setExpireTime(DateUtils.addSeconds(new Date(), CODE_CACHE_EXPIRE));

        // 异步发送验证码
        MailRequest mailRequest = MailRequest.builder()
                .sender(sender)
                .receive(uniqueKey)
                .subject(String.format("hugAi-%s-验证码", SmsTypeEnum.REGISTER.getDescription()))
                .content(String.format(Optional.ofNullable(content).orElse("%s"), code))
                .build();
        rabbitTemplate.convertAndSend(MQConstants.Exchange.topic, MQConstants.Queue.sms, mailRequest);
//        messageService.send(ChannelEnum.mail_send, mailRequest);
        // redis存储短信实体
        this.updateEntity(smsVerifyEnum, uniqueKey, smsParam);
        String countKey = KEY_CODE + uniqueKey + ":COUNT";
        if (!redisUtils.redisTemplate.hasKey(countKey)) {
            redisUtils.setCacheObject(countKey, 1, 60L, TimeUnit.SECONDS);
        } else {
            redisUtils.incr(countKey);
        }
        return code;
    }

    /**
     * 更新短信实体
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @param smsParam
     */
    @Override
    public void updateEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey, SmsBaseParam smsParam) {
        String key = KEY_CODE + smsParam.getUniqueKey();
        hashOperations.put(key, smsVerifyEnum.name(), smsParam);
    }

    /**
     * 获取key的发送次数
     *
     * @param uniqueKey
     * @return
     */
    @Override
    public int countSendCache(String uniqueKey) {
        String countKey = KEY_CODE + uniqueKey + ":COUNT";
        return (int) Optional.ofNullable(redisUtils.getCacheObject(countKey)).orElse(0);
    }

    /**
     * 验证短信验证码
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @param code
     * @return {@link SmsCodeEnum}
     */
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
        if (!smsParam.getValue().equals(code)) {
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

    /**
     * 获取验证码
     *
     * @param smsEntity
     * @return
     */
    private String getCode(SmsBaseParam smsEntity) {
        if (StrUtil.isNotEmpty(smsEntity.getValue())
                && smsEntity.getStatus().equals(Boolean.FALSE)
                && Objects.nonNull(smsEntity.getExpireTime())
                && Instant.now().toEpochMilli() < smsEntity.getExpireTime().getTime()) {
            return smsEntity.getValue();
        }
        // 新的code码
        return (Math.random() + "").substring(2, 2 + 6);
    }

    /**
     * 获取短信实体
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @return
     */
    @Override
    public SmsBaseParam getEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey) {
        String key = KEY_CODE + uniqueKey;
        return hashOperations.get(key, smsVerifyEnum.name());
    }

}
