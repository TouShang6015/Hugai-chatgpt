package com.hugai.core.chat.sessionType.strategy;

import com.hugai.common.enums.SessionType;
import org.springframework.stereotype.Service;

/**
 * CHAT 会话类型策略实现
 *
 * @author WuHao
 * @since 2023/11/30 14:49
 */
@Service
public class SessionChatBusinessServiceStrategy extends SessionBusinessCommon {

    @Override
    public SessionType type() {
        return SessionType.chat;
    }

}
