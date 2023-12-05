package com.hugai.core.drawTask.manager.queue;

import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.modules.draw.service.TaskDrawService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author WuHao
 * @since 2023/10/1 14:56
 */
@Component
public class DrawTaskOpenaiQueueManager extends DrawTaskDataManager {

    @Resource
    private TaskDrawService taskDrawService;

    public DrawTaskOpenaiQueueManager() {
        super(1, 300, 20, 5);
    }

    /**
     * 任务超时回调
     *
     * @param id
     */
    @Override
    protected void timeOutCallBack(String id) {

    }

    /**
     * 执行异常回调
     *
     * @param id
     * @param exception
     */
    @Override
    protected void runBeforeException(String id, Exception exception) {
        taskDrawService.failTask(id, wrapper -> {
            wrapper.set(TaskDrawModel::getRemark, exception.getMessage());
        });
        this.overQueue(id);
    }

}
