package com.hugai.listener;

import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.context.ChatBusinessServiceContext;
import com.hugai.common.constants.MQConstants;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.core.chat.account.SdkAccountBuildContext;
import com.hugai.core.drawTask.context.DrawApiServiceContext;
import com.hugai.core.drawTask.manager.queue.DrawTaskMjQueueManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskOpenaiQueueManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskSdQueueManager;
import com.hugai.core.drawTask.strategy.DrawApiService;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.spring.SpringUtils;
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
    @Resource
    private SessionInfoDrawService sessionInfoDrawService;
    @Resource
    private SessionRecordDrawService sessionRecordDrawService;
    @Resource
    private SdkAccountBuildContext sdkAccountBuildContext;
    @Resource
    private ChatBusinessServiceContext chatBusinessServiceContext;

    @RabbitListener(queues = MQConstants.Queue.draw_openai)
    public void drawListenerOpenai(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_openai, JSON.toJSONString(data));
        try {

            DrawApiService drawApiService = DrawApiServiceContext.init(data, null, sdkAccountBuildContext, chatBusinessServiceContext).getDrawApiService();
            drawApiService.executeApi();

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 任务进行中。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
            log.error("[绘图任务 - {}] 任务失败。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());

            // 更新状态，释放队列
            SpringUtils.getBean(DrawTaskOpenaiQueueManager.class).overQueue(String.valueOf(data.getId()));
            taskDrawService.failTask(String.valueOf(data.getId()), consumer -> {
            });
        }
    }

    @RabbitListener(queues = MQConstants.Queue.draw_sd)
    public void drawListenerSD(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_sd, JSON.toJSONString(data));
        try {

            DrawApiService drawApiService = DrawApiServiceContext.init(data, null, sdkAccountBuildContext, chatBusinessServiceContext).getDrawApiService();
            drawApiService.executeApi();

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 任务进行中。 任务ID： {}", MQConstants.Queue.draw_sd, data.getId());
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
            log.error("[绘图任务 - {}] 任务失败。 任务ID： {}", MQConstants.Queue.draw_sd, data.getId());

            // 更新状态，释放队列
            SpringUtils.getBean(DrawTaskSdQueueManager.class).overQueue(String.valueOf(data.getId()));
            taskDrawService.failTask(String.valueOf(data.getId()), consumer -> {
            });
        }
    }

    @RabbitListener(queues = MQConstants.Queue.draw_mj)
    public void drawListenerMJ(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_mj, JSON.toJSONString(data));
        try {

            DrawApiService drawApiService = DrawApiServiceContext.init(data, null, sdkAccountBuildContext, chatBusinessServiceContext).getDrawApiService();
            drawApiService.executeApi();

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 任务进行中。 任务ID： {}", MQConstants.Queue.draw_mj, data.getId());
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
            log.error("[绘图任务 - {}] 处理失败。 任务ID： {}", MQConstants.Queue.draw_mj, data.getId());

            // 更新状态，释放队列
            SpringUtils.getBean(DrawTaskMjQueueManager.class).overQueue(String.valueOf(data.getId()));
            taskDrawService.failTask(String.valueOf(data.getId()), consumer -> {
            });
        }
    }

}
