package com.hugai.config;

import com.hugai.common.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WuHao
 * @since 2023/9/1 14:55
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    /**
     * 队列 邮箱验证码发送
     *
     * @return
     */
    @Bean
    public Queue queueSms() {
        return QueueBuilder.durable(MQConstants.Queue.sms)
                .build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MQConstants.Exchange.topic);
    }

    @Bean
    public Binding bindingTopicSmsMessage() {
        return BindingBuilder.bind(queueSms()).to(topicExchange()).with(MQConstants.Queue.sms);
    }

}
