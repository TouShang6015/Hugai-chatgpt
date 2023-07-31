package com.hugai.modules.session.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.core.openai.enums.RoleEnum;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.session.mapper.SessionRecordMapper;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 会话详情 业务实现类
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Service
public class SessionRecordServiceImpl extends ServiceImpl<SessionRecordMapper, SessionRecordModel> implements SessionRecordService {

    @Resource
    private ListOperations<String, SessionRecordModel> listOperations;
    @Resource
    private SessionInfoService sessionInfoService;

    /**
     * 聊天响应数据持久化操作
     *
     * @param contextParam
     * @param recordList
     */
    @Transactional
    @Override
    public void responseInsertHandle(SessionCacheData contextParam, List<SessionRecordModel> recordList, Consumer<List<SessionRecordModel>> recordListConsumer) {
        final Long sessionId = contextParam.getSessionId();

        Assert.notNull(sessionId);

        recordListConsumer.accept(recordList);

        // 更新session的总token使用量
        SessionInfoModel sessionInfoModel = sessionInfoService.getById(sessionId);
        sessionInfoModel.setAllConsumerToken(
                sessionInfoModel.getAllConsumerToken() + recordList.stream().map(SessionRecordModel::getConsumerToken).filter(Objects::nonNull).reduce(Integer::sum).orElse(0)
        );
        // 赋值会话名称
        OR.run(sessionInfoModel.getSessionName(), StrUtil::isEmpty, () -> {
            String sessionShowName = recordList.stream().filter(item -> Constants.BOOLEAN.TRUE.equals(item.getIfShow()) && RoleEnum.user.name().equals(item.getRole())).map(SessionRecordModel::getContent).findFirst().orElse("");
            sessionShowName = StrUtil.sub(sessionShowName, 0, 90);
            sessionInfoModel.setSessionName(sessionShowName);
        });
        sessionInfoService.updateById(sessionInfoModel);

        // 持久化响应信息
        this.cachePushRecord(recordList, contextParam.getUserId());
    }

    /**
     * 获取列表根据sessionId
     *
     * @param sessionId
     * @return
     */
    @Override
    public List<SessionRecordModel> cacheGetListBySessionId(Long sessionId) {
        Assert.notNull(sessionId, () -> new BusinessException("sessionId不能为空"));

        final String key = CACHE_KEY_PREFIX + sessionId;

        List<SessionRecordModel> list;

        list = listOperations.range(key, 0, -1);
        if (CollUtil.isEmpty(list)) {
            list = super.lambdaQuery().eq(SessionRecordModel::getSessionId, sessionId).list();
            OR.run(list, CollUtil::isNotEmpty, (item) -> {
                long timeOut = RandomUtil.randomLong(1000 * 60 * 30, 1000 * 60 * 60);
                listOperations.rightPushAll(key, item);
                listOperations.getOperations().expire(key, timeOut, TimeUnit.MILLISECONDS);
            });
        }
        return list;
    }

    /**
     * 添加记录
     *
     * @param param
     */
    @Transactional
    @Override
    public void cachePushRecord(List<SessionRecordModel> param, Long userId) {
        List<Long> sessionIds = param.stream()
                .map(SessionRecordModel::getSessionId).distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Assert.isFalse(CollUtil.isEmpty(sessionIds), () -> new BusinessException("sessionId不能为空"));
        Assert.isFalse(sessionIds.size() > 1, () -> new BusinessException("数据异常"));

        Long sessionId = sessionIds.get(0);

        OptionalUtil.ofNullList(param).forEach(item -> item.setUserId(userId));
        if (super.saveBatch(param)) {
            // 添加缓存，缓存添加失败了就回滚
            final String key = CACHE_KEY_PREFIX + sessionId;

            long timeOut = RandomUtil.randomLong(1000 * 60 * 30, 1000 * 60 * 60);

            if (!listOperations.getOperations().hasKey(key)) {
                List<SessionRecordModel> recordModelList = super.lambdaQuery().eq(SessionRecordModel::getSessionId, sessionId).list();
                Assert.isFalse(
                        listOperations.rightPushAll(key, recordModelList) == 0L
                        , () -> new BusinessException("缓存添加异常"));
                Assert.isFalse(
                        !listOperations.getOperations().expire(key, timeOut, TimeUnit.MILLISECONDS)
                        , () -> new BusinessException("缓存添加异常"));
            } else {
                Assert.notNull(
                        listOperations.rightPushAll(key, param)
                        , () -> new BusinessException("缓存添加异常")
                );
            }
        }
    }

    /**
     * 删除缓存
     *
     * @param sessionIds
     */
    @Override
    public void cacheDeleteRecord(List<Long> sessionIds) {
        if (CollUtil.isEmpty(sessionIds))
            return;
        List<String> keys = sessionIds.stream().map(sessionId -> CACHE_KEY_PREFIX + sessionId).distinct().collect(Collectors.toList());
        listOperations.getOperations().delete(keys);
    }

    /**
     * 刷新缓存
     *
     * @param sessionIds
     */
    @Override
    public void cacheFlushRecord(List<Long> sessionIds) {
        if (CollUtil.isEmpty(sessionIds))
            return;
        List<Tuple2<Long, List<SessionRecordModel>>> sessionRecordListTuple2 = sessionIds.stream().map(sessionId -> {
            List<SessionRecordModel> recordList = super.lambdaQuery().eq(SessionRecordModel::getSessionId, sessionId).list();
            return Tuples.of(sessionId, OptionalUtil.ofNullList(recordList));
        }).collect(Collectors.toList());
        sessionRecordListTuple2.forEach(tuple2 -> {
            Long sessionId = tuple2.getT1();
            String key = CACHE_KEY_PREFIX + sessionId;
            listOperations.rightPushAll(key, tuple2.getT2());
        });
    }
}
