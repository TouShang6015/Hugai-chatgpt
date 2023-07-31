package com.hugai.core.mail.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.enums.ChannelEnum;
import com.hugai.core.mail.entity.MailRequest;
import com.hugai.framework.asyncMessage.annotation.MessageListener;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/7/25 9:54
 */
@Slf4j
@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;

    @MessageListener(ChannelEnum.mail_send)
    public void asyncSendMail(byte[] data) {
        MailRequest param = JSON.parseObject(new String(data, Charset.defaultCharset()), MailRequest.class);
        this.sendMail(param, null);
    }

    /**
     * 邮件发送
     *
     * @param param
     */
    public void sendMail(MailRequest param, Consumer<MimeMessageHelper> helperConsumer) {
        try {
            log.info("邮件发送 - 请求参数：{}", JSON.toJSONString(param));
            ValidatorUtil.validateEntity(param);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            if (Objects.isNull(helperConsumer)) {
                this.helperConsumer(param).accept(helper);
            } else {
                helperConsumer.accept(helper);
            }
            mailSender.send(mimeMessage);
            log.info("邮件发送完成：{}", JSON.toJSONString(param));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("邮件发送失败");
        }
    }

    public Consumer<MimeMessageHelper> helperConsumer(MailRequest request) {
        return helper -> {
            try {
                helper.setFrom(request.getSender());
                helper.setTo(request.getReceive().split(","));
                helper.setSubject(request.getSubject());
                helper.setText(request.getContent());
                helper.setSentDate(new Date());
                String filePath = request.getFilePath();
                if (StrUtil.isNotEmpty(filePath)) {
                    FileSystemResource file = new FileSystemResource(new File(filePath));
                    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                    helper.addAttachment(fileName, file);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new BusinessException("邮件发送失败");
            }
        };
    }

}
