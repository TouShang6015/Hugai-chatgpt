package com.hugai.core.websocket.endpoint;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.RedisCacheKey;
import com.hugai.common.websocket.constants.EndpointConstant;
import com.hugai.common.websocket.constants.ResultCode;
import com.hugai.common.websocket.entity.SocketUserResult;
import com.hugai.core.websocket.pool.UserSocketPool;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/8/22 8:44
 */
@Slf4j
@Component
@ServerEndpoint(value = EndpointConstant.user)
public class SocketPointUser {

    private Session session;

    private String userId;

    private Date connectDate;

    private Date connectCreateTime;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        if (StrUtil.isEmpty(userId)) {
            session.close();
            return;
        }
        this.session = session;
        this.userId = userId;
        this.connectDate = DateUtils.getNowDate();
        this.connectCreateTime = DateUtils.getNowDate();
        UserSocketPool.add(this.userId, this);
        session.getBasicRemote().sendText(JSON.toJSONString(SocketUserResult.i(session.getId())));
//        log.debug("[用户Socket连接] - 用户ID：{}", this.userId);
        // 访问次数统计
        SpringUtils.getBean(RedisUtil.class).incr(RedisCacheKey.WebClientRequestCount);
    }

    @OnMessage
    public void onMessage(String message) {
        SocketUserResult result = JSON.parseObject(message, SocketUserResult.class);
        if (Objects.isNull(result)) {
            return;
        }
        if (!SocketUserResult.verify(result)) {
            return;
        }
        // 更新连接时间
        if (Objects.equals(result.getCode(), ResultCode.C_FLUSH_CONNECT.getCode())) {
            this.connectDate = DateUtils.getNowDate();
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        this.closeSession();
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        this.closeSession();
        throwable.printStackTrace();
    }

    /**
     * 消息推送
     */
    public void sendMessage(SocketUserResult result) {
        if (Objects.isNull(result)) {
            return;
        }
        if (!SocketUserResult.verify(result)) {
            return;
        }
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭socket连接
     */
    public void closeSession() {
        String sessionId = session.getId();
        UserSocketPool.remove(this.userId, sessionId);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public String getUserId() {
        return userId;
    }

    public Date getConnectDate() {
        return connectDate;
    }

    public Date getConnectCreateTime() {
        return connectCreateTime;
    }
}
