package com.hugai.modules.chat.service;

import com.hugai.core.session.entity.SessionCacheData;

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
    void sendChatMessage(SessionCacheData param);

    /**
     * 领域会话发送消息
     *
     * @param param
     */
    void sendDomainMessage(SessionCacheData param);

}
