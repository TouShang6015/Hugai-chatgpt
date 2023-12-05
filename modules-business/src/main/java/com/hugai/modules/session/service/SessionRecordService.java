package com.hugai.modules.session.service;

import cn.hutool.core.collection.CollUtil;
import com.hugai.chatsdk.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.entity.session.RecordData;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 会话详情 业务接口
 *
 * @author WuHao
 * @date 2023-05-29
 */
public interface SessionRecordService extends IService<SessionRecordModel> {

    String CACHE_KEY_PREFIX = "SESSION_RECORD:";

    /**
     * 聊天响应数据持久化操作
     *
     * @param contextParam
     * @param response
     */
    void responseInsertHandle(List<RecordData> requestRecordList, ChatRequestParam contextParam, ChatSdkStorageResponse response);

    /**
     * 获取列表根据sessionId
     *
     * @param sessionId
     * @return
     */
    List<SessionRecordModel> cacheGetListBySessionId(Long sessionId);

    /**
     * 添加记录
     *
     * @param param
     */
    default void cachePushRecord(SessionRecordModel param, Long userId) {
        this.cachePushRecord(CollUtil.newArrayList(param), userId);
    }

    /**
     * 添加记录
     *
     * @param param
     */
    void cachePushRecord(List<SessionRecordModel> param, Long userId);

    /**
     * 删除缓存
     *
     * @param sessionIds
     */
    void cacheDeleteRecord(List<Long> sessionIds);

    /**
     * 刷新缓存
     *
     * @param sessionIds
     */
    void cacheFlushRecord(List<Long> sessionIds);

    /**
     * @deprecated
     */
    @Override
    default boolean save(SessionRecordModel entity) {
        throw new BusinessException("无效的方法");
    }

    /**
     * @deprecated
     */
    @Override
    default boolean saveBatch(Collection<SessionRecordModel> entityList) {
        throw new BusinessException("无效的方法");
    }
}
