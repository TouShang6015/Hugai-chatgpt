package com.hugai.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author wuhao
 */
@Component
@Slf4j
public class ShutdownManager {

    @Resource
    private ScheduledExecutorService scheduledExecutorService;

    @PreDestroy
    public void destroy() {
        try {
            scheduledExecutorService.shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
