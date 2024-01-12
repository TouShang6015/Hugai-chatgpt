package com.hugai.chatsdk.baidu.entity.response;

import lombok.Data;

/**
 * @author WuHao
 * @since 2024/1/12 10:59
 */
@Data
public class ChatResponse {
    private String id;
    private String object;
    private Integer created;
    private Integer sentence_id;
    private Boolean is_end;
    private String result;
    private Boolean need_clear_history;
    private Usage usage;
}
