package com.hugai.core.session.sessionType.context;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.hugai.core.session.sessionType.service.BusinessChatService;
import com.hugai.core.session.sessionType.service.BusinessDomainService;
import com.hugai.core.session.sessionType.strategy.ChatSessionStrategyImpl;
import com.hugai.core.session.sessionType.strategy.OnceSessionStrategyImpl;
import com.hugai.core.session.sessionType.strategy.domain.DomainDefaultImpl;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话业务处理上下文
 *
 * @author WuHao
 * @since 2023/6/5 13:28
 */
@Slf4j
public class SessionBusinessContext {

    private final List<BusinessChatService> businessChatServices;

    private final List<BusinessDomainService> businessDomainServices;

    private SessionBusinessContext() {

        businessChatServices = new ArrayList<BusinessChatService>();
        businessChatServices.addAll(
                CollUtil.newArrayList(
                        new ChatSessionStrategyImpl(),
                        new OnceSessionStrategyImpl()
                )
        );

        businessDomainServices = new ArrayList<BusinessDomainService>();
        businessDomainServices.addAll(
                CollUtil.newArrayList(
                        new DomainDefaultImpl()
                )
        );

        businessChatServices.addAll(businessDomainServices);
    }

    public static SessionBusinessContext createContext() {
        return new SessionBusinessContext();
    }

    /**
     * 初始化chat api服务
     *
     * @param sessionTypeKey
     * @return
     */
    public SessionBusinessContextBuild<BusinessChatService> initChatService(String sessionTypeKey) {
        Assert.notEmpty(sessionTypeKey, () -> new BusinessException("会话类型不能为空"));

        BusinessChatService businessService = businessChatServices.stream()
                .filter(item -> item.sessionTypeSign().getKey().equals(sessionTypeKey))
                .findFirst()
                .orElseThrow(() -> new BusinessException(String.format("会话类型[%s]没有找到可用的策略。", sessionTypeKey)));

        return new SessionBusinessContextBuild<>(businessService);
    }

    /**
     * 初始化领域api服务
     *
     * @param sessionTypeKey
     * @return
     */
    public SessionBusinessContextBuild<BusinessDomainService> initDomainService(String sessionTypeKey) {
        Assert.notEmpty(sessionTypeKey, () -> new BusinessException("会话类型不能为空"));

        BusinessDomainService businessService = businessDomainServices.stream()
                .filter(item -> item.sessionTypeSign().getKey().equals(sessionTypeKey))
                .findFirst()
                .orElseThrow(() -> new BusinessException(String.format("会话类型[%s]没有找到可用的策略。", sessionTypeKey)));

        return new SessionBusinessContextBuild<>(businessService);
    }

}
