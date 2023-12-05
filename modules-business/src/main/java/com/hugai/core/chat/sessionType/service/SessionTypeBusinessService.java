package com.hugai.core.chat.sessionType.service;

import com.hugai.common.enums.SessionType;
import com.hugai.common.modules.entity.session.model.DomainModel;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;

import java.util.List;

/**
 * 会话类型业务接口
 *
 * @author WuHao
 * @since 2023/11/30 14:03
 */
public interface SessionTypeBusinessService {

    /**
     * 标识
     *
     * @return
     */
    SessionType type();

    /**
     * 创建会话前前置操作
     *
     * @param domainModel
     * @return
     */
    List<SessionRecordModel> createSessionBeforeAddRecord(DomainModel domainModel);

}
