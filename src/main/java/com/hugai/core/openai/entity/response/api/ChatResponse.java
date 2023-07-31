package com.hugai.core.openai.entity.response.api;

import com.hugai.core.openai.entity.response.ApiResponse;
import lombok.*;

/**
 * @author WuHao
 * @since 2023/6/1 15:30
 */
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse extends ApiResponse {

    /**
     * 索引
     */
    private Integer index;

    /**
     * 响应角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

    private StringBuilder contentSB;

    /**
     * stop 当前请求已完全输出
     */
    private String finishReason;

}
