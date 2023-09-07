package com.hugai.modules.draw.service;

import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.org.bebas.mapper.service.IService;

import java.util.HashMap;

/**
 * @author WuHao
 * @since 2023/9/7 17:08
 */
public interface TaskDrawService extends IService<TaskDrawModel> {

    /**
     * 创建任务
     *
     * @param apiKey
     * @param paramMap
     */
    void createTask(String apiKey, HashMap<String, Object> paramMap);
}
