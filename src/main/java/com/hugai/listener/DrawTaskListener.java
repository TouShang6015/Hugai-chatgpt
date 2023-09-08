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
    public void drawListener(TaskDrawModel data, Channel channel, Message message) throws IOException {
        log.info("[MQ - {}] 接收消息: {}", MQConstants.Queue.draw_openai, JSON.toJSONString(data));
        try {
            DrawPersistenceCollection collection = handleDrawListener(data);
            taskDrawService.lambdaUpdate()
                    .set(TaskDrawModel::getTaskStatus, TaskStatus.SUCCESS.getKey())
                    .set(TaskDrawModel::getSessionInfoDrawId, collection.getSessionInfoDrawModelInsert().getId())
                    .set(TaskDrawModel::getTaskEndTime, DateUtils.nowDateFormat())
                    .eq(TaskDrawModel::getId, data.getId()).update();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("[绘图任务 - {}] 处理完成。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[绘图任务 - {}] 处理失败。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());
            boolean update = taskDrawService.lambdaUpdate().set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey()).eq(TaskDrawModel::getId, data.getId()).update();
            if (!update) {
                log.error("[绘图任务 - {}] 数据更新失败。 任务ID： {}", MQConstants.Queue.draw_openai, data.getId());
            }
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    @Transactional
    public DrawPersistenceCollection handleDrawListener(TaskDrawModel data) {
        DrawApiService drawApiService = DrawApiServiceContext.init(data).getDrawApiService();
        DrawPersistenceCollection COLLECTION = drawApiService.executeApi();

        OR.run(COLLECTION.getSessionInfoDrawModelInsert(), Objects::nonNull, sessionInfoDrawService::save);
        OR.run(COLLECTION.getSessionRecordDrawModelListInsert(), CollUtil::isNotEmpty, sessionRecordDrawService::saveBatch);

        return COLLECTION;
    }

}
