package com.hugai.modules.session.service;

import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 绘图会话详情 业务接口
 *
 * @author WuHao
 * @date 2023-05-29
 */
public interface SessionRecordDrawService extends IService<SessionRecordDrawModel> {

    /**
     * 根据会话主键获取列表
     *
     * @param sessionDrawInfoId
     * @return
     */
    List<SessionRecordDrawModel> cacheGetBySessionInfoId(Long sessionDrawInfoId);

}
