package com.hugai.chatsdk.ollama.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WuHao
 * @since 2024/5/25 1:30
 */
@NoArgsConstructor
@Data
public class ChatCompletionsResponse {

    @JsonProperty("model")
    private String model;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("messages")
    private List<Message> messages;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("done")
    private Boolean done;

    @JsonProperty("total_duration")
    private Long totalDuration;

    @JsonProperty("load_duration")
    private Integer loadDuration;

    @JsonProperty("prompt_eval_count")
    private Integer promptEvalCount;

    @JsonProperty("prompt_eval_duration")
    private Integer promptEvalDuration;

    @JsonProperty("eval_count")
    private Integer evalCount;

    @JsonProperty("eval_duration")
    private Long evalDuration;

}
