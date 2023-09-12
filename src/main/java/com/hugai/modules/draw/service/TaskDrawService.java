package com.hugai.modules.draw.service;

import com.hugai.modules.draw.entity.model.TaskDrawModel;
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
     * @param apiKey   {@link com.hugai.common.enums.flow.DrawType.ApiKey}
     * @param paramMap
     */
    void createTask(String apiKey, HashMap<String, Object> paramMap);

    /**
     * 任务状态结束更新操作
     *
     * @param serviceConsumer
     */
    void endTaskUpdate(Consumer<TaskDrawService> serviceConsumer, Long userId);

}
