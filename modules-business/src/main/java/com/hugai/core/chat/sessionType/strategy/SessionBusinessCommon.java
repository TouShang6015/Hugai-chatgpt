package com.hugai.core.chat.sessionType.strategy;

import com.hugai.common.modules.entity.session.model.DomainModel;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.hugai.core.chat.sessionType.service.SessionTypeBusinessService;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/11/30 14:49
 */
public abstract class SessionBusinessCommon implements SessionTypeBusinessService {

    @Override
    public List<SessionRecordModel> createSessionBeforeAddRecord(DomainModel domainModel) {
        return null;
    }
}
