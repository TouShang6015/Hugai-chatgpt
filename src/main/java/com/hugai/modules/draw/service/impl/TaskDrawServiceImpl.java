package com.hugai.modules.draw.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.drawTask.valid.CreateTask;
import com.hugai.core.midjourney.manager.TaskQueueManager;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.mapper.TaskDrawMapper;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.hugai.modules.session.mapper.SessionInfoDrawMapper;
import com.hugai.modules.session.mapper.SessionRecordDrawMapper;
import com.hugai.modules.system.entity.vo.baseResource.ResourceDrawVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.DateUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/9/7 17:09
 */
@Service
public class TaskDrawServiceImpl extends ServiceImpl<TaskDrawMapper, TaskDrawModel> implements TaskDrawService {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private SessionInfoDrawMapper drawMapper;
    @Resource
    private SessionRecordDrawMapper recordDrawMapper;

    /**
     * 创建任务
     *  @param apiKey
     * @param paramMap
     */
    @Transactional
    @Override
    public void createTask(String apiKey, HashMap<String, Object> paramMap) {
        Long userId = SecurityContextUtil.getUserId();

        DrawType.ApiKey apiKeyValue = DrawType.ApiKey.getByName(apiKey);
        DrawType drawType = DrawType.getByApiKey(apiKeyValue);
        if (drawType.equals(DrawType.OPENAI)) {
            ResourceDrawVO resourceDraw = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceDraw();
            Assert.isFalse(!resourceDraw.getOpenDrawOpenai(), () -> new BusinessException("openai绘图功能暂时关闭了"));
        }

        // 校验
        Object requestParamObject = JSON.parseObject(JSON.toJSONString(paramMap), apiKeyValue.getMappingCls());
        ValidatorUtil.validateEntity(requestParamObject, CreateTask.class);

        Assert.isFalse(
                super.lambdaQuery().eq(TaskDrawModel::getUserId, userId)
                        .eq(TaskDrawModel::getDrawType, drawType.getKey())
                        .in(TaskDrawModel::getTaskStatus, CollUtil.newArrayList(TaskStatus.WAIT, TaskStatus.RUNNING))
                        .count() > 0,
                () -> new BusinessException("有任务正在进行中~请等待任务执行完成")
        );

        TaskDrawModel taskDrawModel = TaskDrawModel.builder()
                .userId(userId)
                .drawType(drawType.getKey())
                .drawApiKey(apiKeyValue.name())
                .taskStatus(TaskStatus.WAIT.getKey())
                .requestParam(JSON.toJSONString(paramMap))
                .build();

        super.save(taskDrawModel);

        log.info("绘图任务创建 - 消息发送完成，channel: {},param:{}", drawType.queueKey(), JSON.toJSONString(taskDrawModel));
        rabbitTemplate.convertAndSend(drawType.queueKey(), taskDrawModel);
    }

    @Transactional
    @Override
    public void failTask(String id, Consumer<LambdaUpdateWrapper<TaskDrawModel>> paramConsumer) {
        Assert.notNull(id);
        LambdaUpdateWrapper<TaskDrawModel> wrapper = Wrappers.lambdaUpdate();
        paramConsumer.accept(wrapper);
        wrapper.eq(TaskDrawModel::getId, id);
        wrapper.set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey())
                .set(TaskDrawModel::getTaskEndTime, DateUtils.nowDateFormat());
        super.update(wrapper);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        List<TaskDrawModel> taskDrawModelList = lambdaQuery().in(TaskDrawModel::getId, list).list();
        List<Long> ids = taskDrawModelList.stream().peek(item -> Assert.isFalse(
                TaskStatus.SUCCESS.getKey().equals(item.getTaskStatus()),
                () -> new BusinessException("已完成的任务无法删除")
        )).map(TaskDrawModel::getId).distinct().collect(Collectors.toList());
        // 删除子表
        drawMapper.delete(Wrappers.<SessionInfoDrawModel>lambdaQuery().in(SessionInfoDrawModel::getTaskId,ids));
        recordDrawMapper.delete(Wrappers.<SessionRecordDrawModel>lambdaQuery().in(SessionRecordDrawModel::getTaskId,ids));
        // 删除主表
        super.removeByIds(ids);
        // 删任务
        Map<String, DrawTaskDataManager> drawTaskDataManagerMap = SpringUtil.getBeansOfType(DrawTaskDataManager.class);
        drawTaskDataManagerMap.forEach((k,v) -> {
            ids.forEach(id -> {
                OR.run(TaskQueueManager.get(String.valueOf(id)), Objects::nonNull, taskObj -> {
                    SpringUtils.getBean(MidjourneyTaskEventListener.class).endTask(taskObj);
                });
                v.overQueue(String.valueOf(id));
            });
        });
        return true;
    }
}
