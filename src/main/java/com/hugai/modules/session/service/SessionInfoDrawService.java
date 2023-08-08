package com.hugai.modules.session.service;

import com.hugai.modules.session.entity.dto.SessionInfoDrawDTO;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.org.bebas.mapper.service.IService;

/**
 * 绘图会话 业务接口
 *
 * @author WuHao
 * @date 2023-05-29
 */
public interface SessionInfoDrawService extends IService<SessionInfoDrawModel> {

    /**
     * 获取最新的会话详情
     *
     * @param drawUniqueKey
     * @return
     */
    SessionInfoDrawDTO getLastSession(String drawUniqueKey);

}
