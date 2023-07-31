package com.hugai.core.session.sessionType.service;

import com.hugai.core.session.sessionType.TypeSign;
import com.hugai.modules.session.entity.dto.SessionRecordDTO;
import com.hugai.modules.session.entity.model.SessionRecordModel;

import java.util.List;

/**
 * 策略业务接口
 *
 * @author WuHao
 * @since 2023/6/5 13:24
 */
public interface BusinessService extends SessionCacheDataService<BusinessService>, TypeSign {

    /**
     * 新增会话时初始化一条会话信息
     *
     * @return
     */
    SessionRecordDTO addSessionInitFirstRecord();

    /**
     * 计算会话可请求的有效上下文信息
     *
     * @return
     */
    List<SessionRecordModel> getSessionValidRequestContext();

}
