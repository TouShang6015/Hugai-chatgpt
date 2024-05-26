package com.hugai.chatsdk.ollama.entity;

import lombok.Data;

/**
 * @author WuHao
 * @since 2024/5/25 3:11
 */
@Data
public class Message {

    private String role;

    private String content;

    /**
     *  (optional) a list of base64-encoded images (for multimodal models such as llava)
     */
    private String images;

}
