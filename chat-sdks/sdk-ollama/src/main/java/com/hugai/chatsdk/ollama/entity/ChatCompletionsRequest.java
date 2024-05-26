package com.hugai.chatsdk.ollama.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author WuHao
 * @since 2024/5/25 1:30
 */
@NoArgsConstructor
@Data
public class ChatCompletionsRequest {

    private String model;

    private List<Message> messages;

    private Boolean stream;

    private String format;

    private Map<Object,Object> options;

}
