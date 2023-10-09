package com.hugai.core.midjourney.manager;

import cn.hutool.core.util.StrUtil;
import com.hugai.core.midjourney.common.entity.TaskObj;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

/**
 * mj任务管理
 *
 * @author WuHao
 * @since 2023/9/27 13:13
 */
public class TaskQueueManager {

    private static final ConcurrentLinkedQueue<TaskObj> TASK_QUEUE;

    private static final String suffix = "--v 5.2";

    static {
        TASK_QUEUE = new ConcurrentLinkedQueue<>();
    }

    public static void add(TaskObj bean) {
        if (Objects.isNull(bean))
            return;
        TASK_QUEUE.add(bean);
    }

    public static TaskObj get(Predicate<TaskObj> predicate) {
        return TASK_QUEUE.stream().filter(predicate).findFirst().orElse(null);
    }

    public static TaskObj get(String taskId) {
        if (StrUtil.isEmpty(taskId))
            return null;
        return get(obj -> taskId.equals(obj.getNonce()));
    }

    public static TaskObj get(String prompt, String guildId, String channelId, String targetHandler) {
        return get(bean -> {
            if (StrUtil.isEmpty(targetHandler)) {
                return false;
            }
            if (!targetHandler.equals(bean.getTargetHandler())) {
                return false;
            }
            Predicate<String> verifyPrompt = argPrompt -> StrUtil.isNotEmpty(argPrompt) && !argPrompt.equals(bean.getPrompt());
            if (verifyPrompt.test(prompt)) {
                if (verifyPrompt.test(prompt.trim())) {
                    if (verifyPrompt.test(prompt.replace(suffix, ""))) {
                        if (verifyPrompt.test(
                                prompt.replace(suffix, "").trim()
                        )) {
                            return false;
                        }
                    }
                }
            }
            if (StrUtil.isNotEmpty(guildId) && !guildId.equals(bean.getGuildId())) {
                return false;
            }
            if (StrUtil.isNotEmpty(channelId) && !channelId.equals(bean.getChannelId())) {
                return false;
            }
            return true;
        });
    }

    public static boolean remove(Predicate<TaskObj> predicate) {
        return TASK_QUEUE.removeIf(predicate);
    }

    public static boolean remove(TaskObj obj) {
        if (Objects.isNull(obj))
            return false;
        return TASK_QUEUE.remove(obj);
    }

}
