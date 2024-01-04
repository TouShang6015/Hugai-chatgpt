package com.hugai.common.support.sms.strategy.plugins;

import com.hugai.common.support.sms.entity.SmsBaseParam;
import com.hugai.common.support.sms.enums.SmsStrategy;
import com.hugai.common.support.sms.strategy.SendServiceCommon;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.common.webApi.log.SysLogSmsWebApi;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author WuHao
 * @since 2023/12/26 13:44
 */
@Component
public class SendEmailStrategy extends SendServiceCommon {

    @Resource
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public SendEmailStrategy(RedisUtil redisUtils, HashOperations<String, Object, SmsBaseParam> hashOperations, SysLogSmsWebApi logSmsWebApi, BaseResourceWebApi resourceConfigWebApi) {
        super(redisUtils, hashOperations, logSmsWebApi, resourceConfigWebApi);
    }

    @Override
    public SmsStrategy type() {
        return SmsStrategy.mail;
    }

    @Override
    protected void sendExecute(SmsBaseParam param) {

        String uniqueKey = param.getUniqueKey();
        String code = param.getCode();
        String sendText = this.buildSendCodeText(code);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(sender);
            helper.setTo(uniqueKey.split(","));
            helper.setSubject("HugAi");
            helper.setText(sendText);
            helper.setSentDate(new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new BusinessException("邮件发送失败");
        }
        mailSender.send(mimeMessage);
    }

}
