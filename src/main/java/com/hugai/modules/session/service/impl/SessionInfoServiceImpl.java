package com.hugai.modules.session.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.common.enums.flow.SessionStatus;
import com.hugai.common.enums.flow.SessionType;
import com.hugai.core.openai.enums.RoleEnum;
import com.hugai.core.openai.utils.TokenCalculateUtil;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.session.sessionType.context.SessionBusinessContext;
import com.hugai.core.session.sessionType.service.BusinessDomainService;
import com.hugai.modules.session.entity.dto.SessionRecordDTO;
import com.hugai.modules.session.entity.model.DomainModel;
import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.session.mapper.SessionInfoMapper;
import com.hugai.modules.session.service.DomainService;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.flowenum.utils.FlowEnumUtils;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;
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

    /**
     * 新增会话
     *
     * @param sessionType
     */
    @Transactional
    @Override
    public SessionInfoModel addSession(String sessionType) {
        SecurityContextUtil.verifyIsNormalUser();

        SessionType sessionTypeEnum = FlowEnumUtils.getEnumByKey(sessionType, SessionType.class);
        Assert.notNull(sessionTypeEnum, () -> new BusinessException("sessionType不在系统预设中，请传递正确的sessionType参数"));

        Long userId = SecurityContextUtil.getUserId();

        SessionInfoModel lastSessionInfo = super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType).orderByDesc(SessionInfoModel::getSessionNum)
                .last("limit 1").one();
        Integer sessionNum = Optional.ofNullable(lastSessionInfo).orElseGet(SessionInfoModel::new).getSessionNum();

        // 最新的会话内容如果为0 则不新增会话，使用原来的会话
        if (Objects.nonNull(lastSessionInfo)) {
            if (sessionRecordService.lambdaQuery()
                    .eq(SessionRecordModel::getUserId, userId)
                    .eq(SessionRecordModel::getSessionId, lastSessionInfo.getId())
                    .eq(SessionRecordModel::getIfShow, Constants.BOOLEAN.TRUE)
                    .count() == 0) {
                return lastSessionInfo;
            }
        }

        SessionInfoModel sessionInfoParam = SessionInfoModel.builder()
                .type(sessionType)
                .userId(userId)
                .status(SessionStatus.PROGRESS.getKey())
                .allConsumerToken(0)
                .sessionNum(Optional.ofNullable(sessionNum).orElse(0) + 1)
                .build();

        // 新增主表
        super.save(sessionInfoParam);

        return sessionInfoParam;
    }

    /**
     * 新增领域会话
     *
     * @param sessionType
     * @param domainUniqueKey
     */
    @Transactional
    @Override
    public SessionInfoModel addDomainSession(String sessionType, String domainUniqueKey, String content) {
        SecurityContextUtil.verifyIsNormalUser();

        SessionType sessionTypeEnum = FlowEnumUtils.getEnumByKey(sessionType, SessionType.class);
        Assert.notNull(sessionTypeEnum, () -> new BusinessException("sessionType不在系统预设中，请传递正确的sessionType参数"));

        DomainModel domainModel = domainService.lambdaQuery().eq(DomainModel::getUniqueKey, domainUniqueKey).one();
        Assert.notNull(domainModel, () -> new BusinessException("未找到对应的领域会话信息，请检查参数[domainUniqueKey]是否正确"));

        Long userId = SecurityContextUtil.getUserId();

        SessionInfoModel lastSessionInfo = super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType)
                .eq(SessionInfoModel::getDomainUniqueKey, domainUniqueKey)
                .orderByDesc(SessionInfoModel::getSessionNum)
                .last("limit 1").one();
        Integer sessionNum = Optional.ofNullable(lastSessionInfo).orElseGet(SessionInfoModel::new).getSessionNum();

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
                .sessionNum(Optional.ofNullable(sessionNum).orElse(0) + 1)
                .domainUniqueKey(domainUniqueKey)
                .build();

        // 新增主表
        super.save(sessionInfoParam);
        final Long sessionId = sessionInfoParam.getId();

        BusinessDomainService service = SessionBusinessContext.createContext().initDomainService(sessionType).getService();
        service.initCacheData(cacheData -> {
            cacheData.setContent(Optional.ofNullable(content).orElse(""));
            cacheData.setSessionId(sessionId);
            cacheData.setDomainUniqueKey(domainUniqueKey);
            cacheData.setUserId(userId);
        });
        List<SessionRecordModel> pushList = CollUtil.newArrayList();
        // 添加一条介绍的会话消息
        Supplier<SessionRecordModel> getFirstContentModel = () -> {
            DomainModel domainModelCache = service.getDomainModel();
            String firstContent = domainModelCache.getFirstContent();
            if (StrUtil.isEmpty(firstContent)) {
                return null;
            }
            SessionRecordModel model = new SessionRecordModel();
            model.setUserId(userId);
            model.setSessionId(sessionId);
            model.setDomainUniqueKey(domainModelCache.getUniqueKey());
            model.setRole(RoleEnum.system.name());
            model.setContent(firstContent);
            model.setIfShow(Constants.BOOLEAN.TRUE);
            model.setIfContext(Constants.BOOLEAN.FALSE);
            model.setIfDomainTop(Constants.BOOLEAN.FALSE);
            model.setConsumerToken(0);
            return model;
        };
        // 新增子表
        OR.run(service.addSessionInitFirstRecord(), Objects::nonNull, pushList::add);
        OR.run(getFirstContentModel.get(), Objects::nonNull, pushList::add);
        sessionRecordService.cachePushRecord(pushList,userId);

        return sessionInfoParam;
    }

    /**
     * 绘画会话新增
     *
     * @param sessionType
     * @param drawType
     * @return
     */
    @Override
    public SessionInfoModel addDrawSession(String sessionType, String drawType) {
        SecurityContextUtil.verifyIsNormalUser();

        SessionType sessionTypeEnum = FlowEnumUtils.getEnumByKey(sessionType, SessionType.class);
        Assert.notNull(sessionTypeEnum, () -> new BusinessException("sessionType不在系统预设中，请传递正确的sessionType参数"));
        DrawType drawTypeEnum = FlowEnumUtils.getEnumByKey(drawType, DrawType.class);
        Assert.notNull(drawTypeEnum, () -> new BusinessException("drawType不在系统预设中，请传递正确的drawType参数"));

        Long userId = SecurityContextUtil.getUserId();

        SessionInfoModel lastSessionInfo = super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType)
                .eq(SessionInfoModel::getDrawUniqueKey, drawType)
                .orderByDesc(SessionInfoModel::getSessionNum)
                .last("limit 1").one();
        Integer sessionNum = Optional.ofNullable(lastSessionInfo).orElseGet(SessionInfoModel::new).getSessionNum();

        // 最新的会话内容如果为0 则不新增会话，使用原来的会话
        if (Objects.nonNull(lastSessionInfo)) {
            if (sessionRecordService.lambdaQuery()
                    .eq(SessionRecordModel::getUserId, userId)
                    .eq(SessionRecordModel::getSessionId, lastSessionInfo.getId())
                    .eq(SessionRecordModel::getIfShow, Constants.BOOLEAN.TRUE)
                    .count() == 0) {
                return lastSessionInfo;
            }
        }

        SessionInfoModel sessionInfoParam = SessionInfoModel.builder()
                .type(sessionType)
                .userId(userId)
                .status(SessionStatus.PROGRESS.getKey())
                .drawUniqueKey(drawType)
                .allConsumerToken(0)
                .sessionNum(Optional.ofNullable(sessionNum).orElse(0) + 1)
                .build();

        // 新增主表
        super.save(sessionInfoParam);

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
     * 获取用户最新画图会话
     *
     * @param sessionType
     * @param drawUniqueKey
     * @return
     */
    @Override
    public SessionInfoModel userLastDrawSession(String sessionType, String drawUniqueKey) {
        Long userId = SecurityContextUtil.getUserId();

        return super.lambdaQuery()
                .eq(SessionInfoModel::getUserId, userId)
                .eq(SessionInfoModel::getType, sessionType)
                .eq(SessionInfoModel::getDrawUniqueKey, drawUniqueKey)
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
