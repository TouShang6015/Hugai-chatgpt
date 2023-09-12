package com.hugai.listener;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.MQConstants;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.core.drawTask.context.DrawApiServiceContext;
import com.hugai.core.drawTask.strategy.DrawApiService;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.utils.DateUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;

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

    @RabbitListener(queues = MQConstants.Queue.draw_openai)
    public void drawListenerOpenai(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_openai, JSON.toJSONString(data));
        try {
            handleDrawListener(data, (taskServiceConsumer, dataCollection) -> {
                taskServiceConsumer.endTaskUpdate(service -> {
                    service.lambdaUpdate()
                            .set(TaskDrawModel::getTaskStatus, TaskStatus.SUCCESS.getKey())
                            .set(TaskDrawModel::getSessionInfoDrawId, dataCollection.getSessionInfoDrawModelInsert().getId())
                            .set(TaskDrawModel::getTaskEndTime, DateUtils.nowDateFormat())
                            .set(TaskDrawModel::getShowImg, dataCollection.getSessionInfoDrawModelInsert().getShowImg())
                            .eq(TaskDrawModel::getId, data.getId()).update();
                }, data.getUserId());
            });

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 处理完成。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[绘图任务 - {}] 处理失败。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());
            taskDrawService.endTaskUpdate(service -> taskDrawService.lambdaUpdate()
                            .set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey())
                            .eq(TaskDrawModel::getId, data.getId()).update()
                    , data.getUserId()
            );
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    @RabbitListener(queues = MQConstants.Queue.draw_sd)
    public void drawListenerSD(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_sd, JSON.toJSONString(data));
        try {

            handleDrawListener(data, (taskServiceConsumer, dataCollection) -> {
                taskServiceConsumer.endTaskUpdate(service -> {
                    service.lambdaUpdate()
                            .set(TaskDrawModel::getTaskStatus, TaskStatus.SUCCESS.getKey())
                            .set(TaskDrawModel::getSessionInfoDrawId, dataCollection.getSessionInfoDrawModelInsert().getId())
                            .set(TaskDrawModel::getTaskEndTime, DateUtils.nowDateFormat())
                            .set(TaskDrawModel::getShowImg, dataCollection.getSessionInfoDrawModelInsert().getShowImg())
                            .eq(TaskDrawModel::getId, data.getId()).update();
                }, data.getUserId());
            });

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 处理完成。 任务ID： {}", MQConstants.Queue.draw_sd, data.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[绘图任务 - {}] 处理失败。 任务ID： {}", MQConstants.Queue.draw_sd, data.getId());
            taskDrawService.endTaskUpdate(service -> taskDrawService.lambdaUpdate()
                            .set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey())
                            .eq(TaskDrawModel::getId, data.getId()).update()
                    , data.getUserId()
            );
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    /**
     * 数据处理
     *
     * @param data
     * @param taskServiceConsumer -   为了保证在一个事务中，将taskService的update信息也添加事务中
     * @return
     */
    @Transactional
    public DrawPersistenceCollection handleDrawListener(TaskDrawModel data, BiConsumer<TaskDrawService, DrawPersistenceCollection> taskServiceConsumer) {
        DrawApiService drawApiService = DrawApiServiceContext.init(data).getDrawApiService();
        DrawPersistenceCollection COLLECTION = drawApiService.executeApi();

        OR.run(COLLECTION.getSessionInfoDrawModelInsert(), Objects::nonNull, sessionInfoDrawService::save);
        OR.run(COLLECTION.getSessionRecordDrawModelListInsert(), CollUtil::isNotEmpty, sessionRecordDrawService::saveBatch);
        taskServiceConsumer.accept(taskDrawService, COLLECTION);

        return COLLECTION;
    }

}
