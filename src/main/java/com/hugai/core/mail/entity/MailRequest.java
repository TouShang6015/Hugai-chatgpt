package com.hugai.core.mail.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author WuHao
 * @since 2023/7/25 9:55
 */
@Builder
@Data
public class MailRequest implements Serializable {

    /**
     * 发送人
     */
    @NotEmpty(message = "发件人不能为空")
    private String sender;
    /**
     * 接收人
     */
    @NotEmpty(message = "接收人不能为空")
    private String receive;
    /**
     *  邮件主题
     */
    @NotEmpty(message = "邮件主题不能为空")
    private String subject;

    /**
     *  内容
     */
    @NotEmpty(message = "邮件内容不能为空")
    private String content;

    /**
     *  附件路径
     */
    private String filePath;

}
