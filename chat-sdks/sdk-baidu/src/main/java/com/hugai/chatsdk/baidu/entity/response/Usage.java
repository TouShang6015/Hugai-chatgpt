package com.hugai.chatsdk.baidu.entity.response;

import lombok.Data;

/**
 * @author WuHao
 * @since 2024/1/12 11:01
 */
@Data
public class Usage {


    /**
     * 问题tokens数
     */
    private Integer prompt_tokens;
    /**
     * 回答tokens数
     */
    private Integer completion_tokens;
    /**
     * tokens总数
     */
    private Integer total_tokens;

}
