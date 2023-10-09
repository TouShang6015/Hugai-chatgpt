package com.hugai.core.drawTask.manager.queue;

import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.modules.draw.service.TaskDrawService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author WuHao
 * @since 2023/10/1 14:17
 */
@Component
public class DrawTaskMjQueueManager extends DrawTaskDataManager {

    @Resource
    private MidjourneyTaskEventListener taskEventListener;
    @Resource
    private TaskDrawService taskDrawService;

    public DrawTaskMjQueueManager() {
        super(3, 350, 10, 300);
    }

    /**
     * 任务超时回调
     *
     * @param id
     */
    @Override
    protected void timeOutCallBack(String id) {
        taskEventListener.errorTask(id, "任务超时");
    }

    /**
     * 执行异常回调
     *
     * @param id
     * @param exception
     */
    @Override
    protected void runBeforeException(String id, Exception exception) {
        taskEventListener.errorTask(id, "超过最大任务数");
    }

}
