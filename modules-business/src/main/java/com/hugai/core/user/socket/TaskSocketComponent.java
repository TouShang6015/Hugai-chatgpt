package com.hugai.core.user.socket;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.user.socket.manager.UserSocketGlobalData;
import com.hugai.common.websocket.constants.ResultCode;
import com.hugai.core.websocket.endpoint.SocketPointUser;
import com.hugai.core.websocket.pool.UserSocketPool;
import com.org.bebas.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户Socket定时任务组件
 *
 * @author WuHao
 * @since 2023/10/16 14:03
 */
@Slf4j
@Component("taskSocketComponent")
public class TaskSocketComponent {

    public static final int expiredTime = 30;

    /**
     * 清理无用的socket连接
     */
    public void clearUserSocket() {
        ConcurrentHashMap<String, Map<String, SocketPointUser>> cache = UserSocketPool.CACHE;
        Date nowDate = DateUtils.getNowDate();

        AtomicInteger counter = new AtomicInteger(0);
        if (CollUtil.isNotEmpty(cache)){
            cache.entrySet().parallelStream().forEach(entry -> {
                Map<String, SocketPointUser> valueMap = entry.getValue();
                if (CollUtil.isNotEmpty(valueMap)){
                    valueMap.forEach((key, socketPointUser) -> {
                        Date connectDate = socketPointUser.getConnectDate();
                        if (DateUtils.addMinutes(connectDate, expiredTime).getTime() < nowDate.getTime()) {
                            socketPointUser.closeSession();
                            counter.getAndIncrement();
                        }
                    });
                }
            });
        }
        log.debug("[User Socket任务 - 清理无用的socket连接] 时间：{},清理完成：{}个", DateUtils.nowDateFormat(), counter.get());
    }

    /**
     * 刷新在线人数
     */
    public void flushOnlineAmount() {
        long onlineAmount = UserMessagePushUtil.getOnlineAmount();
        UserSocketGlobalData.onlineAmount = onlineAmount == 0L ? 1L : onlineAmount;
        UserMessagePushUtil.broadcastMessage(ResultCode.S_ONLINE_COUNT, null, onlineAmount);
//        log.debug("[User Socket任务 - 刷新在线人数] 时间：{},在线人数：{}人", DateUtils.nowDateFormat(), onlineAmount);
    }

}
