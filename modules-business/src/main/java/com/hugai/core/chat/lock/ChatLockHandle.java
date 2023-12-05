package com.hugai.core.chat.lock;

import com.org.bebas.constants.RedisConstant;
import com.org.bebas.core.function.SingleFunction;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 会话锁任务处理
 *
 * @author WuHao
 * @since 2023/6/7 14:20
 */
@Slf4j
public class ChatLockHandle {

    // 默认超时时间 秒
    private final static long DEFAULT_TIME_OUT = 60 * 10;

    private ChatLockHandle() {
    }

    private ChatLockHandle(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 分组名称
     */
    private String groupName;

    private static final RedissonClient redissonClient;

    static {
        redissonClient = SpringUtils.getBean(RedissonClient.class);
    }

    /**
     * 初始化
     *
     * @param groupName
     * @return
     */
    public static ChatLockHandle init(String groupName) {
        return new ChatLockHandle(groupName);
    }

    /**
     * 获取会话redis锁
     *
     * @param sessionType
     * @param sessionId
     * @return
     */
    public RLock getLock(String sessionType, Long sessionId) {
        final String KEY = RedisConstant.REDIS_LOCK + groupName + ":" + sessionType + ":" + sessionId;
        return redissonClient.getLock(KEY);
    }

    /**
     * 获取领域会话redis锁
     *
     * @param sessionType
     * @param sessionId
     * @param domainUniqueKey
     * @return
     */
    public RLock getLock(String sessionType, Long sessionId, String domainUniqueKey) {
        final String KEY = RedisConstant.REDIS_LOCK + groupName + ":" + sessionType + ":" + domainUniqueKey + ":" + sessionId;
        return redissonClient.getLock(KEY);
    }


    /**
     * chat handle
     *
     * @param sessionType
     * @param sessionId
     * @param singleFunction
     * @param errorMessage
     */
    public void handle(String sessionType, Long sessionId, SingleFunction singleFunction, String errorMessage) {
        final String KEY = RedisConstant.REDIS_LOCK + groupName + ":" + sessionType + ":" + sessionId;

        RLock lock = getLock(sessionType, sessionId);

        if (!lock.isLocked()) {
            lock.lock(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            log.info("[Redis Lock {}] - 加锁: {}", groupName, KEY);
        } else {
            log.info("[Redis Lock {}] - 占用中: {}", groupName, KEY);
            throw new BusinessException(errorMessage);
        }

        try {
            if (lock.isLocked()) {
                log.info("[Redis Lock {}] - 锁正在执行: {}", groupName, KEY);
                singleFunction.run();
            } else {
                throw new BusinessException(errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                log.info("[Redis Lock {}] - 释放锁: {}", groupName, KEY);
                lock.unlock();
            }
        }
    }

    /**
     * domain handle
     *
     * @param sessionType
     * @param sessionId
     * @param domainUniqueKey
     * @param singleFunction
     * @param errorMessage
     */
    public void handle(String sessionType, Long sessionId, String domainUniqueKey, SingleFunction singleFunction, String errorMessage) {
        final String KEY = RedisConstant.REDIS_LOCK + groupName + ":" + sessionType + ":" + domainUniqueKey + ":" + sessionId;

        RLock lock = getLock(sessionType, sessionId, domainUniqueKey);

        if (!lock.isLocked()) {
            lock.lock(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            log.info("[Redis Lock {}] - 加锁: {}", groupName, KEY);
        } else {
            log.info("[Redis Lock {}] - 占用中: {}", groupName, KEY);
            throw new BusinessException(errorMessage);
        }

        try {
            if (lock.isLocked()) {
                log.info("[Redis Lock {}] - 锁正在执行: {}", groupName, KEY);
                singleFunction.run();
            } else {
                throw new BusinessException(errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                log.info("[Redis Lock {}] - 释放锁: {}", groupName, KEY);
                lock.unlock();
            }
        }
    }

}
