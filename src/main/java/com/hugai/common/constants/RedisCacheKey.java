package com.hugai.common.constants;

/**
 * @author WuHao
 * @since 2023/8/18 13:29
 */
public interface RedisCacheKey {

    /**
     * 统计访问次数key
     */
    String WebClientRequestCount = "WEB_CLIENT_REQUEST_COUNT";

    /**
     * 当前openai绘图任务队列
     */
    String TASK_DRAW_QUEUE_OPENAI = "TASK_DRAW_QUEUE:OPENAI";
    /**
     * 当前sd绘图任务队列
     */
    String TASK_DRAW_QUEUE_SD = "TASK_DRAW_QUEUE:SD";
    /**
     * 当前mj任务队列
     */
    String TASK_DRAW_QUEUE_MJ = "TASK_DRAW_QUEUE:MJ";

}
