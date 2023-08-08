package com.hugai.modules.session.service.impl;

import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.session.entity.convert.SessionInfoDrawConvert;
import com.hugai.modules.session.entity.dto.SessionInfoDrawDTO;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.hugai.modules.session.mapper.SessionInfoDrawMapper;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 会话表 业务实现类
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Service
public class SessionInfoDrawServiceImpl extends ServiceImpl<SessionInfoDrawMapper, SessionInfoDrawModel> implements SessionInfoDrawService {

    @Resource
    private SessionRecordDrawService sessionRecordDrawService;

    /**
     * 获取最新的会话详情
     *
     * @param drawUniqueKey
     * @return
     */
    @Override
    public SessionInfoDrawDTO getLastSession(String drawUniqueKey) {
        Long userId = SecurityContextUtil.getUserId();

        SessionInfoDrawModel lastSession = super.lambdaQuery()
                .eq(SessionInfoDrawModel::getUserId, userId)
                .eq(SessionInfoDrawModel::getDrawUniqueKey, drawUniqueKey)
                .orderByDesc(SessionInfoDrawModel::getCreateTime)
                .last("limit 1").one();

        SessionInfoDrawDTO dto = SessionInfoDrawConvert.INSTANCE.convertToDTO(lastSession);

        OR.run(dto, Objects::nonNull, () -> {
            Long sessionDrawId = dto.getId();
            dto.setRecordList(
                    sessionRecordDrawService.lambdaQuery().eq(SessionRecordDrawModel::getSessionInfoDrawId, sessionDrawId).list()
            );
        });

        return dto;
    }
}
