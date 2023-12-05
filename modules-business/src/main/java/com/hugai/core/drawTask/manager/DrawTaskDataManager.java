package com.hugai.core.drawTask.manager;

import cn.hutool.core.lang.Assert;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.function.SingleFunction;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author WuHao
 * @since 2023/10/1 13:49
 */
@Slf4j
public abstract class DrawTaskDataManager {

    protected ThreadPoolExecutor executor;

    protected final Map<String, CountDownLatch> stateMap;

    protected int waitTime;

    /**
     * @param runningCount  -   最大并发数
     * @param timeout       -   空闲线程存活时间（秒）
     * @param maxQueueCount -   最大任务等待数
     * @param waitTime      -   任务最大等待时间（秒）
     */
    public DrawTaskDataManager(int runningCount, int timeout, int maxQueueCount, int waitTime) {
        this.executor = new ThreadPoolExecutor(runningCount, runningCount, timeout, TimeUnit.SECONDS, new ArrayBlockingQueue<>(maxQueueCount));
        this.waitTime = waitTime;
        this.stateMap = new ConcurrentHashMap<>();
    }

    /**
     * 开始任务
     *
     * @param id       -   任务唯一标识
     * @param runnable -
     */
    public void start(String id, SingleFunction runnable) {
        Assert.notEmpty(id, () -> new BusinessException("[Draw Task] 任务开始失败，唯一id不能为空"));
        log.debug("[绘图任务管理 - start] - 任务ID：{}", id);
        try {
            this.executor.execute(() -> {
                try {
                    CountDownLatch latch = new CountDownLatch(1);
                    runnable.run();
                    this.stateMap.put(id, latch);
                    boolean await = latch.await(waitTime, TimeUnit.SECONDS);
                    if (!await) {
                        this.overQueue(id);
                        this.timeOutCallBack(id);
                    }
                    log.debug("[绘图任务管理 - 任务结束] - 任务id：{}", id);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    this.runBeforeException(id, e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.runBeforeException(id, e);
        }
    }

    /**
     * 执行任务，同步方法
     *
     * @param id
     * @param runnable
     */
    public void startSync(String id, SingleFunction runnable) {
        Assert.notEmpty(id, () -> new BusinessException("[Draw Task] 任务开始失败，唯一id不能为空"));
        log.debug("[绘图任务管理 - startSync] - 任务ID：{}", id);
        try {
            this.executor.execute(() -> {
                try {
                    runnable.run();
                    log.debug("[绘图任务管理 - 任务结束] - 任务id：{}", id);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.runBeforeException(id, e);
                } finally {
                    this.overQueue(id);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.runBeforeException(id, e);
        }
    }

    /**
     * 任务唤醒回调
     *
     * @param id
     */
    public void awake(String id, SingleFunction callback) {
        OR.run(this.stateMap.get(id), Objects::nonNull, latch -> {
            try {
                log.debug("[绘图任务管理 - 回调] - 任务ID：{}", id);
                callback.run();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("[Draw Task] 任务回调异常");
            } finally {
                latch.countDown();
                this.overQueue(id);
            }
        });
    }

    /**
     * 任务超时回调
     *
     * @param id
     */
    protected abstract void timeOutCallBack(String id);

    /**
     * 执行异常回调
     *
     * @param id
     * @param exception
     */
    protected abstract void runBeforeException(String id, Exception exception);

    /**
     * 正在运行的任务数量
     *
     * @return
     */
    public int getRunningCount() {
        return this.executor.getActiveCount();
    }

    /**
     * 获取等待任务数
     *
     * @return
     */
    public int getWaitTime() {
        return this.executor.getQueue().size();
    }

    public Map<String, CountDownLatch> getStateMap() {
        return stateMap;
    }

    /**
     * 结束任务
     *
     * @param id
     */
    public void overQueue(String id) {
        OR.run(this.stateMap.get(id), Objects::nonNull, CountDownLatch::countDown);
        this.stateMap.remove(id);
    }
}
