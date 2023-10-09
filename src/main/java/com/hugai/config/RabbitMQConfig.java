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

    private final TopicExchange topicExchange = new TopicExchange(MQConstants.Exchange.topic);

    /**
     * 队列 邮箱验证码发送
     *
     * @return
     */
    @Bean
    public Queue queueSms() {
        return QueueBuilder.durable(MQConstants.Queue.sms).build();
    }

    @Bean
    public Queue queueDrawOpenai() {
        return QueueBuilder.durable(MQConstants.Queue.draw_openai).build();
    }

    @Bean
    public Queue queueDrawSD() {
        return QueueBuilder.durable(MQConstants.Queue.draw_sd).build();
    }

    @Bean
    public Queue queueDrawMJ() {
        return QueueBuilder.durable(MQConstants.Queue.draw_mj).build();
    }

    @Bean
    public Binding bindingTopicSmsMessage() {
        return BindingBuilder.bind(queueSms()).to(topicExchange()).with(MQConstants.Queue.sms);
    }

    @Bean
    public Binding bindingTopicDrawOpenaiMessage() {
        return BindingBuilder.bind(queueDrawOpenai()).to(topicExchange()).with(MQConstants.Queue.draw_openai);
    }

    @Bean
    public Binding bindingTopicDrawSDMessage() {
        return BindingBuilder.bind(queueDrawSD()).to(topicExchange()).with(MQConstants.Queue.draw_sd);
    }

    @Bean
    public Binding bindingTopicDrawMJMessage() {
        return BindingBuilder.bind(queueDrawMJ()).to(topicExchange()).with(MQConstants.Queue.draw_mj);
    }

    @Bean
    public TopicExchange topicExchange() {
        return topicExchange;
    }

}
