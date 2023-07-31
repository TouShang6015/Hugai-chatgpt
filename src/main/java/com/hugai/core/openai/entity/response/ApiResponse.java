package com.hugai.core.openai.entity.response;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/5/31 15:52
 */
@Data
public class ApiResponse {

    /**
     * 完整响应内容
     * <p>流式请求没有值</p>
     */
    private String body;

    /**
     * token使用量
     */
    private TokenUsageNum tokenUsageNum;

}
