package com.hugai.core.session.sessionType.context;

import cn.hutool.core.lang.Assert;
import com.hugai.core.session.sessionType.service.BusinessService;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuHao
 * @since 2023/6/19 17:30
 */
@Slf4j
public class SessionBusinessContextBuild<Service extends BusinessService> {

    private final Service businessService;

    public SessionBusinessContextBuild(Service businessService) {
        this.businessService = businessService;
        Assert.notNull(this.businessService, () -> {
            log.error("businessService为空，请先设置[sessionType]初始化策略服务");
            return new BusinessException("策略为空");
        });
    }

    public Service getService(Long sessionId) {
        this.businessService.initCacheDataSessionId(sessionId);
        return this.businessService;
    }

    public Service getService() {
        return this.businessService;
    }

}
