package com.hugai.chatsdk.context;

import com.hugai.chatsdk.service.ChatBusinessService;
import com.hugai.common.support.strategy.StrategyServiceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author WuHao
 * @since 2023/11/29 13:59
 */
@Slf4j
@Component
public class ChatBusinessServiceContext extends StrategyServiceContext<ChatBusinessService> {

    @Autowired
    public ChatBusinessServiceContext(List<ChatBusinessService> chatBusinessServices) {
        super(chatBusinessServices);
    }

    @Override
    protected Function<ChatBusinessService, String> serviceKey() {
        return s -> s.type().getKey();
    }
}
