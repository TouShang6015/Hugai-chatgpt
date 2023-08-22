package com.hugai.core.session.sessionType.service;

import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.modules.session.entity.model.DomainModel;
import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.org.bebas.core.function.OR;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 会话数据缓冲 业务接口
 *
 * @author WuHao
 * @since 2023/6/5 13:24
 */
public interface SessionCacheDataService<Service> {

    /**
     * 获取上下文可请求的最大token
     *
     * @return
     */
    int getContextMaxToken();

    /**
     * 获取会话信息
     *
     * @return
     */
    SessionInfoModel getDataSessionInfo();

    /**
     * 获取会话详情列表
     *
     * @return
     */
    List<SessionRecordModel> getDataSessionRecordList();

    /**
     * 设置缓存对象
     *
     * @param cacheDataConsumer
     * @return
     */
    Service initCacheData(Consumer<SessionCacheData> cacheDataConsumer);

    /**
     * 设置缓存对象
     *
     * @param cacheData
     * @return
     */
    default Service initCacheData(SessionCacheData cacheData) {
        return initCacheData(consumer -> {
            OR.run(cacheData.getConnectId(), Objects::nonNull, consumer::setConnectId);
            OR.run(cacheData.getSessionId(), Objects::nonNull, consumer::setSessionId);
            OR.run(cacheData.getDomainGroup(), Objects::nonNull, consumer::setDomainGroup);
            OR.run(cacheData.getToken(), Objects::nonNull, consumer::setToken);
            OR.run(cacheData.getUserId(), Objects::nonNull, consumer::setUserId);
            OR.run(cacheData.getContent(), Objects::nonNull, consumer::setContent);
            OR.run(cacheData.getDomainUniqueKey(), Objects::nonNull, consumer::setDomainUniqueKey);
        });
    }


    /**
     * 设置缓存对象 sessionId
     *
     * @param sessionId
     * @return
     */
    default Service initCacheDataSessionId(Long sessionId) {
        return initCacheData(consumer -> {
            consumer.setSessionId(sessionId);
        });
    }

    /**
     * 获取缓存对象
     *
     * @return
     */
    SessionCacheData getCacheData();

    /**
     * 获取领域配置数据
     *
     * @return
     */
    DomainModel getDomainModel();

    /**
     * 获取默认模型
     * <p>用户输入>系统配置>默认<p/>
     *
     * @return
     */
    String getUseModel();

}
