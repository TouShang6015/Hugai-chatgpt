package com.hugai.listener;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.MQConstants;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.service.TaskDrawService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author WuHao
 * @since 2023/9/7 22:21
 */
@Slf4j
@Component
public class DrawTaskListener {

    @Resource
    private TaskDrawService taskDrawService;

    @RabbitListener(queues = MQConstants.Queue.draw_openai)
    public void sendMailListener(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_openai, JSON.toJSONString(data));
        try {
            Thread.sleep(2000);
            taskDrawService.lambdaUpdate().set(TaskDrawModel::getTaskStatus, TaskStatus.SUCCESS.getKey()).eq(TaskDrawModel::getId, data.getId()).update();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 接收消息: {}", MQConstants.Queue.draw_openai, JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            boolean update = taskDrawService.lambdaUpdate().set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey()).eq(TaskDrawModel::getId, data.getId()).update();
            if (!update){
                log.error("绘画任务执行失败，更新状态失败，请核对队列信息与数据库信息！");
            }
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}
