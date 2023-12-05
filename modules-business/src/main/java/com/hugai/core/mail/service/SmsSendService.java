package com.hugai.core.mail.service;

import com.hugai.core.mail.entity.SmsBaseParam;
import com.hugai.core.mail.enums.SmsCodeEnum;
import com.hugai.core.mail.enums.SmsTypeEnum;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/7/25 11:02
 */
public interface SmsSendService {

    String KEY_CODE = "SMS:";

    /**
     * 短信验证码发送
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @return
     */
    String sendSmsCode(SmsTypeEnum smsVerifyEnum, String uniqueKey, String content);

    /**
     * 验证短信验证码
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @param code
     * @return
     */
    SmsCodeEnum verifyCode(SmsTypeEnum smsVerifyEnum, String uniqueKey, String code);

    /**
     * 获取短信实体
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @return
     */
    SmsBaseParam getEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey);

    /**
     * 验证码更新
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @param smsEntity
     */
    void updateEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey, SmsBaseParam smsEntity);

    /**
     * 获取key的发送次数
     *
     * @param uniqueKey
     * @return
     */
    int countSendCache(String uniqueKey);

    /**
     * 验证码更新
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @param biFunction
     * @param consumer
     */
    default void updateEntity(SmsTypeEnum smsVerifyEnum, String uniqueKey, BiFunction<SmsTypeEnum, String, SmsBaseParam> biFunction, Consumer<SmsBaseParam> consumer) {
        SmsBaseParam entity = biFunction.apply(smsVerifyEnum, uniqueKey);
        if (Objects.isNull(entity)) {
            throw new RuntimeException("请求失败");
        }
        consumer.accept(entity);
        this.updateEntity(smsVerifyEnum, uniqueKey, entity);
    }

    /**
     * 修改验证码状态
     *
     * @param smsVerifyEnum
     * @param uniqueKey
     * @param consumer
     */
    default void changeEntityStatus(SmsTypeEnum smsVerifyEnum, String uniqueKey, Consumer<SmsBaseParam> consumer) {
        this.updateEntity(smsVerifyEnum, uniqueKey, this::getEntity, consumer);
    }

}
