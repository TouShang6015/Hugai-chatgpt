package com.hugai.common.entity.baseResource;

import lombok.Data;

import java.io.Serializable;

/**
 * 短信配置
 *
 * @author wuhao
 * @date 2022/9/23 9:21
 */
@Data
public class ResourceSmsConfigVO implements Serializable {

    /**
     * 默认短信发送策略
     */
    private String defaultStrategy;
    /**
     * 默认短信平台
     */
    private String defaultSmsPlatform;

    /**
     * 有效时间（秒）
     */
    private Integer codeEffectiveTime;
    /**
     * 短信限定时间范围（秒）
     */
    private Integer codeLimitTime;
    /**
     * 限定时间内可发送数量
     */
    private Integer codeLimitCount;
    /**
     * 验证码文本内容
     */
    private String textSendCode;

}
