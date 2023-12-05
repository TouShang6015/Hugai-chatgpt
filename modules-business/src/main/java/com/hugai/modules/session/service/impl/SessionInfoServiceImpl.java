package com.hugai.modules.session.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.flow.SessionStatus;
import com.hugai.common.modules.entity.session.model.SessionInfoModel;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.hugai.common.support.strategy.StrategyServiceContext;
import com.hugai.core.chat.sessionType.service.SessionTypeBusinessService;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.session.mapper.SessionInfoMapper;
import com.hugai.modules.session.service.DomainService;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 会话表 业务实现类
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Service
public class SessionInfoServiceImpl extends ServiceImpl<SessionInfoMapper, SessionInfoModel> implements SessionInfoService {

    @Resource
    private SessionRecordService sessionRecordService;
    @Resource
    private DomainService domainService;
    @Resource
    private StrategyServiceContext<SessionTypeBusinessService> sessionTypeBusinessContext;

    /**
     * 新增领域会话
     *
     * @param sessionType
     * @param domainUniqueKey
     */
    @Transactional
    @Override
    public SessionInfoModel addSession(String sessionType, String domainUniqueKey, String content) {
        SecurityContextUtil.verifyIsNormalUser();

        Long userId = SecurityContextUtil.getUserId();

        SessionInfoModel lastSessionInfo = super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType)
                .eq(StrUtil.isNotEmpty(domainUniqueKey), SessionInfoModel::getDomainUniqueKey, domainUniqueKey)
                .orderByDesc(SessionInfoModel::getId)
                .last("limit 1").one();

        // 最新的会话内容如果为0 则不新增会话，使用原来的会话
        if (Objects.nonNull(lastSessionInfo)) {
            if (sessionRecordService.lambdaQuery()
                    .eq(SessionRecordModel::getUserId, userId)
                    .eq(SessionRecordModel::getSessionId, lastSessionInfo.getId())
                    .eq(SessionRecordModel::getIfShow, Constants.BOOLEAN.TRUE)
                    .eq(SessionRecordModel::getIfContext, Constants.BOOLEAN.TRUE)
                    .count() == 0) {
                return lastSessionInfo;
            }
        }

        SessionInfoModel sessionInfoParam = SessionInfoModel.builder()
                .type(sessionType)
                .userId(userId)
                .status(SessionStatus.PROGRESS.getKey())
                .allConsumerToken(0)
                .domainUniqueKey(domainUniqueKey)
                .build();
        sessionInfoParam.setId(IdWorker.getId());
        // 入库
        super.save(sessionInfoParam);

        // 新增主表
        final Long sessionId = sessionInfoParam.getId();

        // 如果是领域会话
        if (StrUtil.isNotEmpty(domainUniqueKey)) {
            List<SessionRecordModel> pushList = CollUtil.newArrayList();

            OR.run(domainService.getByUniqueKey(domainUniqueKey), Objects::nonNull, domainModel -> {
                SessionTypeBusinessService sessionTypeBusinessService = sessionTypeBusinessContext.getService(sessionType, () -> new BusinessException("没有找到会话类型策略"));

                // 创建会话前前置操作
                OR.run(sessionTypeBusinessService.createSessionBeforeAddRecord(domainModel), Objects::nonNull, domainRecordList -> {
                    domainRecordList.forEach(item -> {
                        item.setUserId(userId);
                        item.setSessionId(sessionId);
                    });
                    pushList.addAll(domainRecordList);
                });

            });

            if (CollUtil.isNotEmpty(pushList)) {
                sessionRecordService.cachePushRecord(pushList, userId);
            }
        }
        return sessionInfoParam;
    }

    /**
     * 获取用户最新会话
     *
     * @param sessionType
     * @return
     */
    @Override
    public SessionInfoModel userLastSession(String sessionType) {
        Long userId = SecurityContextUtil.getUserId();

        return super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType)
                .orderByDesc(SessionInfoModel::getId)
                .last("limit 1")
                .one();
    }

    /**
     * 获取用户最新会话（领域会话）
     *
     * @param sessionType
     * @param domainUniqueKey
     * @return
     */
    @Override
    public SessionInfoModel userLastDomainSession(String sessionType, String domainUniqueKey) {
        Long userId = SecurityContextUtil.getUserId();

        return super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType)
                .eq(SessionInfoModel::getDomainUniqueKey, domainUniqueKey)
                .orderByDesc(SessionInfoModel::getId)
                .last("limit 1")
                .one();
    }

    /**
     * 清空会话列表
     *
     * @param sessionId
     */
    @Override
    public void clearSession(Long sessionId) {
        Long userId = SecurityContextUtil.getUserId();
        List<SessionRecordModel> recordModelList = sessionRecordService.lambdaQuery().eq(SessionRecordModel::getUserId, userId).eq(SessionRecordModel::getSessionId, sessionId).list();
        List<Long> updateIds = recordModelList.stream()
                .filter(item -> !Constants.BOOLEAN.TRUE.equals(item.getIfDomainTop()))
                .filter(item -> !(Constants.BOOLEAN.TRUE.equals(item.getIfShow()) && Constants.BOOLEAN.FALSE.equals(item.getIfContext())))
                .map(SessionRecordModel::getId)
                .distinct()
                .collect(Collectors.toList());
        OR.run(updateIds, CollUtil::isNotEmpty, () -> {
            sessionRecordService.lambdaUpdate()
                    .set(SessionRecordModel::getIfShow, Constants.BOOLEAN.FALSE)
                    .set(SessionRecordModel::getIfContext, Constants.BOOLEAN.FALSE)
                    .in(SessionRecordModel::getId, updateIds)
                    .update();
            sessionRecordService.cacheDeleteRecord(CollUtil.newArrayList(sessionId));
        });
    }

    @Override
    public boolean removeById(Serializable id) {
        return this.removeByIds(CollUtil.newArrayList(id));
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        // 删主表
        if (super.removeByIds(list)) {
            // 删子表
            sessionRecordService.lambdaUpdate().in(SessionRecordModel::getSessionId, list).remove();
            // 删缓存
            List<Long> sessionIds = list.stream().map(String::valueOf).map(Long::valueOf).distinct().collect(Collectors.toList());
            sessionRecordService.cacheDeleteRecord(sessionIds);
        }
        return true;
    }
}
