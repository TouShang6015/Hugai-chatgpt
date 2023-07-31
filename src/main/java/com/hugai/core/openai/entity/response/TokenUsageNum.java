package com.hugai.core.openai.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token使用情况对象
 *
 * @author WuHao
 * @since 2023/6/1 14:52
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenUsageNum {

    /**
     * 请求消耗token
     */
    private int requestTokenUseNum;
    /**
     * 响应消耗
     */
    private int responseTokenUseNum;
    /**
     * 总消耗
     */
    private int tokenUseNum;

}
