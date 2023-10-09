package com.hugai.core.midjourney.service;

import com.hugai.core.midjourney.common.entity.TaskObj;

/**
 * mj绘图任务队列处理
 *
 * @author WuHao
 * @since 2023/9/27 14:40
 */
public interface MidjourneyTaskEventListener {

    /**
     * 执行任务
     *
     * @param taskQueueBean
     */
    void taskRun(TaskObj taskQueueBean);

    /**
     * 任务结束
     *
     * @param taskQueueBean
     */
    void endTask(TaskObj taskQueueBean);

    /**
     * 任务异常
     *
     * @param taskId
     */
    void errorTask(String taskId, String error);

    default void errorTask(String taskId) {
        errorTask(taskId, null);
    }

}
