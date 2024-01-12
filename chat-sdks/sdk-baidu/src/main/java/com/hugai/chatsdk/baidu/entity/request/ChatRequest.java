package com.hugai.chatsdk.baidu.entity.request;

import lombok.Data;

import java.util.List;

/**
 * @author WuHao
 * @since 2024/1/12 10:48
 */
@Data
public class ChatRequest {

    private List<Message> messages;

    private Boolean stream;

    private Double temperature;

    private Double top_p;

    private Double penalty_score;

    private String system;

    private String user_id;



}
