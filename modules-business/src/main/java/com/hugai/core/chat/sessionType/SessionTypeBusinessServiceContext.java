package com.hugai.core.chat.sessionType;

import com.hugai.common.support.strategy.StrategyServiceContext;
import com.hugai.core.chat.sessionType.service.SessionTypeBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author WuHao
 * @since 2023/11/30 18:46
 */
@Component
public class SessionTypeBusinessServiceContext extends StrategyServiceContext<SessionTypeBusinessService> {

    @Autowired
    public SessionTypeBusinessServiceContext(List<SessionTypeBusinessService> sessionTypeBusinessServices) {
        super(sessionTypeBusinessServices);
    }

    @Override
    protected Function<SessionTypeBusinessService, String> serviceKey() {
        return s -> s.type().name();
    }
}
