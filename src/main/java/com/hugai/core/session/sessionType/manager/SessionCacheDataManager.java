package com.hugai.core.session.sessionType.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.sessionType.service.SessionCacheDataService;
import com.hugai.core.session.sessionType.TypeSign;
import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 会话策略管理器
 *
 * @author WuHao
 * @since 2023/6/5 13:16
 */
public abstract class SessionCacheDataManager<Service extends TypeSign> implements SessionCacheDataService<Service> {

    private SessionCacheData cacheData;

    // 会话信息
    private SessionInfoModel sessionInfoModel;
    // 会话详情列表
    private List<SessionRecordModel> sessionRecordModelList;

    /**
     * 获取会话信息
     *
     * @return
     */
    @Override
    public SessionInfoModel getDataSessionInfo() {
        OR.run(this.sessionInfoModel, Objects::isNull, arg -> {
            this.sessionInfoModel = SpringUtils.getBean(SessionInfoService.class).getById(this.getSessionId());
        });
        Assert.notNull(this.sessionInfoModel, () -> new BusinessException("不存在的会话信息"));
        return this.sessionInfoModel;
    }

    /**
     * 获取会话详情列表
     *
     * @return
     */
    @Override
    public List<SessionRecordModel> getDataSessionRecordList() {
        OR.run(this.sessionRecordModelList, CollUtil::isEmpty, arg -> {
            this.sessionRecordModelList = SpringUtils.getBean(SessionRecordService.class).cacheGetListBySessionId(this.getSessionId());
        });
        List<SessionRecordModel> recordModelList = OptionalUtil.ofNullList(this.sessionRecordModelList);
        this.cacheData.setToken(
                recordModelList.stream().map(SessionRecordModel::getConsumerToken).filter(Objects::nonNull).reduce(Integer::sum).orElse(0)
        );
        return OptionalUtil.ofNullList(this.sessionRecordModelList);
    }

    /**
     * 设置缓存对象
     *
     * @param cacheDataConsumer
     * @return
     */
    @Override
    public Service initCacheData(Consumer<SessionCacheData> cacheDataConsumer) {
        OR.run(this.cacheData, Objects::isNull, data -> this.cacheData = new SessionCacheData());
        cacheDataConsumer.accept(this.cacheData);
        return (Service) this;
    }

    /**
     * 获取缓存对象
     *
     * @return
     */
    @Override
    public SessionCacheData getCacheData() {
        Assert.notNull(this.cacheData, () -> new BusinessException("会话策略器未初始化缓存参数"));
        return this.cacheData;
    }

    protected Long getSessionId() {
        return this.getCacheData().getSessionId();
    }
}
