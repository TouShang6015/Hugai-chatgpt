package com.hugai.modules.draw.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.core.drawTask.valid.CreateTask;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.mapper.TaskDrawMapper;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.modules.system.entity.vo.baseResource.ResourceDrawVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.DateUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/9/7 17:09
 */
@Service
public class TaskDrawServiceImpl extends ServiceImpl<TaskDrawMapper, TaskDrawModel> implements TaskDrawService {

    @Resource
    private RabbitTemplate rabbitTemplate;

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

}
