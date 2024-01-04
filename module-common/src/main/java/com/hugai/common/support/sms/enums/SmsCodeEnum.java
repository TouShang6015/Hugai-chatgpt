package com.hugai.common.support.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码状态枚举
 *
 * @author wyj
 * @date 2022/10/19 11:44
 */
@Getter
@AllArgsConstructor
public enum SmsCodeEnum {

    SUCCESS(0, "发送成功"),

    CODE_NOT_MATCHING(1, "验证码不正确"),
    NOT_SEND_CODE(2, "未发送短信验证码"),
    PAST_DUE(3, "验证码已过期"),
    ALREADY_USE(4, "验证码已被使用"),
    ;

    private final int code;

    private final String message;

    public boolean success() {
        return this.getCode() == 0;
    }

}
