package com.hugai.modules.session.service;

import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.org.bebas.mapper.service.IService;

/**
 * 会话表 业务接口
 *
 * @author WuHao
 * @date 2023-05-29
 */
public interface SessionInfoService extends IService<SessionInfoModel> {

    /**
     * 新增会话
     *
     * @param sessionType
     */
    SessionInfoModel addSession(String sessionType);

    /**
     * 新增领域会话
     *
     * @param sessionType
     */
    SessionInfoModel addDomainSession(String sessionType, String domainUniqueKey, String content);

    /**
     * 绘画会话新增
     *
     * @param sessionType
     * @param drawType
     * @return
     */
    SessionInfoModel addDrawSession(String sessionType, String drawType);

    /**
     * 获取用户最新会话
     *
     * @param sessionType
     * @return
     */
    SessionInfoModel userLastSession(String sessionType);

    /**
     * 获取用户最新会话（领域会话）
     *
     * @param sessionType
     * @param domainUniqueKey
     * @return
     */
    SessionInfoModel userLastDomainSession(String sessionType, String domainUniqueKey);

    /**
     * 获取用户最新画图会话
     *
     * @param sessionType
     * @return
     */
    SessionInfoModel userLastDrawSession(String sessionType, String drawUniqueKey);

    /**
     * 清空会话列表
     *
     * @param sessionId
     */
    void clearSession(Long sessionId);

}
