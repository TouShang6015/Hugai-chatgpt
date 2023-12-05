package com.hugai.core.websocket.endpoint;

import com.hugai.common.websocket.constants.EndpointConstant;
import com.hugai.common.pool.ChatSocketPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author WuHao
 * @since 2023/8/22 8:44
 */
@Slf4j
@Component
@ServerEndpoint(value = EndpointConstant.chat)
public class SocketPointChat {

    @OnOpen
    public void onOpen(Session session) throws IOException {
        ChatSocketPool.add(session.getId(), session);
        session.getBasicRemote().sendText(session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        ChatSocketPool.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        ChatSocketPool.remove(session.getId());
        throwable.printStackTrace();
    }

}
