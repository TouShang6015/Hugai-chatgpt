package com.hugai.modules.draw.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.core.drawTask.enums.DrawType;
import com.org.bebas.mapper.service.IService;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/9/7 17:08
 */
public interface TaskDrawService extends IService<TaskDrawModel> {

    /**
     * 创建任务
     *
     * @param apiKey   {@link DrawType.ApiKey}
     * @param paramMap
     */
    void createTask(String apiKey, Long originalTaskDrawId, HashMap<String, Object> paramMap);

    default void createTask(String apiKey, HashMap<String, Object> paramMap) {
        createTask(apiKey, null, paramMap);
    }

    /**
     * 任务失败处理
     *
     * @param id
     * @param paramConsumer
     */
    void failTask(String id, Consumer<LambdaUpdateWrapper<TaskDrawModel>> paramConsumer);

}
