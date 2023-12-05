package com.hugai.modules.chat.service;

import com.hugai.core.chat.entity.ChatRequestParam;

/**
 * 聊天会话 业务接口
 *
 * @author WuHao
 * @since 2023/6/6 10:52
 */
public interface ChatService {

    /**
     * 发送消息，聊天或文本
     *
     * @param param
     */
    void sendChatMessage(ChatRequestParam param);

}
