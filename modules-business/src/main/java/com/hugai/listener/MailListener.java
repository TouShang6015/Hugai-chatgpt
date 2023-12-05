package com.hugai.listener;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.MQConstants;
import com.hugai.core.mail.entity.MailRequest;
import com.hugai.core.mail.service.impl.MailService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 邮箱mq监听
 *
 * @author WuHao
 * @since 2023/9/7 22:19
 */
@Slf4j
@Component
public class MailListener {

    @Resource
    private MailService mailService;

    @RabbitListener(queues = MQConstants.Queue.sms)
    public void sendMailListener(MailRequest data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.sms, JSON.toJSONString(data));
        try {
            mailService.sendMail(data, null);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}
