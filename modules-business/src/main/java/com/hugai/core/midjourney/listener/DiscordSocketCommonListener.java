package com.hugai.core.midjourney.listener;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.listener.core.SocketMessageHandlerEvent;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.api.utils.data.DataType;
import net.dv8tion.jda.internal.requests.WebSocketCode;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 消息接收处理
 *
 * @author WuHao
 * @since https://github.com/novicezk/midjourney-proxy
 */
@Slf4j
public class DiscordSocketCommonListener extends WebSocketListener {

    protected DiscordAccount discordAccount;
    protected WebSocket socket;

    private Future<?> heartbeatInterval;
    private Future<?> heartbeatTimeout;
    private final ScheduledExecutorService heartExecutor;

    private String sessionId;
    private String resumeGatewayUrl;
    private boolean heartbeatAck = false;
    private Object sequence = null;
    private long interval = 41250;
    private boolean trying = false;

    public DiscordSocketCommonListener(DiscordAccount discordAccount) {
        this.discordAccount = discordAccount;
        this.heartExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    protected void handleMessage(DataObject data) {
        int opCode = data.getInt("op");
        switch (opCode) {
            case WebSocketCode.HEARTBEAT:
                log.info("[Discord] - Account：{} | Receive heartbeat.", this.discordAccount.getUserName());
                handleHeartbeat();
                break;
            case WebSocketCode.HEARTBEAT_ACK:
                this.heartbeatAck = true;
                clearHeartbeatTimeout();
                break;
            case WebSocketCode.HELLO:
                handleHello(data);
                doResumeOrIdentify();
                break;
            case WebSocketCode.RESUME:
                log.info("[Discord] - Account：{} | Receive resumed.", this.discordAccount.getUserName());
                connectSuccess();
                break;
            case WebSocketCode.RECONNECT:
                reconnect("receive server reconnect");
                break;
            case WebSocketCode.INVALIDATE_SESSION:
                close(1009, "receive session invalid");
                break;
            case WebSocketCode.DISPATCH:
                handleDispatch(data);
                break;
            default:
                log.info("[Discord] - account:{} Receive unknown code: {}.", this.discordAccount.getUserName(), data);
                break;
        }
    }

    private void doResumeOrIdentify() {
        if (CharSequenceUtil.isBlank(this.sessionId)) {
            log.info("[Discord] - Account：{} | Send identify msg.", this.discordAccount.getUserName());
            send(WebSocketCode.IDENTIFY, this.discordAccount.getAutoData());
        } else {
            log.info("[Discord] - Account:{} | Send resume msg.", this.discordAccount.getUserName());
            send(WebSocketCode.RESUME, DataObject.empty()
                    .put("token", this.discordAccount.getUserToken())
                    .put("session_id", this.sessionId)
                    .put("seq", this.sequence)
            );
        }
    }

    private void connectSuccess() {
        this.trying = false;
    }

    private void handleDispatch(DataObject raw) {
        this.sequence = raw.opt("s").orElse(null);
        if (!raw.isType("d", DataType.OBJECT)) {
            return;
        }
        DataObject content = raw.getObject("d");
        String t = raw.getString("t", null);
        if ("READY".equals(t)) {
            this.sessionId = content.getString("session_id");
            this.resumeGatewayUrl = content.getString("resume_gateway_url");
            DiscordSocketAccountPool.update(this.discordAccount.getUserName(), this.sessionId);
            log.info("[Discord] sessionId Update，Account：{},sessionId : {}", this.discordAccount.getUserName(), this.sessionId);
            connectSuccess();
            return;
        }
        try {
            SocketMessageHandlerEvent.init(discordAccount, raw).execute();
        } catch (Exception e) {
            log.error("[Discord] - Account：{} | Handle message error | raw: {}", this.discordAccount.getUserName(), raw.toString());
        }
    }

    private void handleHeartbeat() {
        send(WebSocketCode.HEARTBEAT, this.sequence);
        this.heartbeatTimeout = ThreadUtil.execAsync(() -> {
            ThreadUtil.sleep(this.interval);
            reconnect("heartbeat has not ack");
        });
    }

    private void handleHello(DataObject data) {
        clearHeartbeatInterval();
        this.interval = data.getObject("d").getLong("heartbeat_interval");
        this.heartbeatAck = true;
        this.heartbeatInterval = this.heartExecutor.scheduleAtFixedRate(() -> {
            if (this.heartbeatAck) {
                this.heartbeatAck = false;
                send(WebSocketCode.HEARTBEAT, this.sequence);
            } else {
                reconnect("heartbeat has not ack interval");
            }
        }, (long) Math.floor(RandomUtil.randomDouble(0, 1) * this.interval), this.interval, TimeUnit.MILLISECONDS);
    }

    protected void send(int op, Object d) {
        if (this.socket != null) {
            this.socket.send(DataObject.empty().put("op", op).put("d", d).toString());
        }
    }

    protected void reconnect(String reason) {
        close(2001, reason);
    }

    protected void close(int code, String reason) {
        if (this.socket != null) {
            this.socket.close(code, reason);
        }
    }

    private void clearHeartbeatInterval() {
        if (this.heartbeatInterval != null) {
            this.heartbeatInterval.cancel(true);
            this.heartbeatInterval = null;
        }
    }

    private void clearHeartbeatTimeout() {
        if (this.heartbeatTimeout != null) {
            this.heartbeatTimeout.cancel(true);
            this.heartbeatTimeout = null;
        }
    }

    protected void cancel() {
        this.clearHeartbeatInterval();
        this.clearHeartbeatTimeout();
    }

}
