package com.hugai.core.mail.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author WuHao
 * @since 2023/7/25 10:51
 */
@Data
public class SmsBaseParam implements Serializable {


    /**
     * 短信类型 {@link com.hugai.core.mail.enums.SmsTypeEnum}
     */
    @NotNull(message = "短信类型不能为空")
    private String type;
    /**
     * 唯一标识
     */
    private String uniqueKey;
    /**
     * 短信内容
     */
    private String value;
    /**
     * 发送次数
     */
    private Integer count = 0;
    /**
     * 是否已使用 0 否 1 是
     */
    private Boolean status = Boolean.FALSE;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 到期时间
     */
    private Date expireTime;


}
