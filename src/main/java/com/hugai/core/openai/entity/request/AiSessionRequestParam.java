package com.hugai.core.openai.entity.request;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/5/29 10:26
 */
@Data
public class AiSessionRequestParam {

    /**
     * 会话
     */
    private String sessionId;

    /**
     * 会话详情
     */
    private String sessionRecordId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 会话缓存id
     */
    private String sessionCacheId;

    /**
     * sse id
     */
    private String sseId;
}
