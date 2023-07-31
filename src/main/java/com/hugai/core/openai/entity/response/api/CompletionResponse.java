package com.hugai.core.openai.entity.response.api;

import com.hugai.core.openai.entity.response.ApiResponse;
import lombok.*;

/**
 * @author WuHao
 * @since 2023/6/19 11:02
 */
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletionResponse extends ApiResponse {

    /**
     * 索引
     */
    private Integer index;

    /**
     * 内容
     */
    private String content;
    /**
     * 临时角色（持久化区分用户输入还是系统响应）
     */
    private String role;

    /**
     * stop 当前请求已完全输出
     */
    private String finishReason;

}
