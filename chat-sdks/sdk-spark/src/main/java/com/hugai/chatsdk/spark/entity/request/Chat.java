package com.hugai.chatsdk.spark.entity.request;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/12/27 11:39
 */
@Data
public class Chat {

    private String domain;

    private Double temperature;

    private Integer max_tokens;

    private Integer top_k;

    private String chat_id;

}
