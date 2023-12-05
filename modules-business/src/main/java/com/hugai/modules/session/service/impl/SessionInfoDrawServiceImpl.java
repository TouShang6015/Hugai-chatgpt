package com.hugai.modules.session.service.impl;

import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.common.modules.entity.session.convert.SessionInfoDrawConvert;
import com.hugai.common.modules.entity.session.dto.SessionInfoDrawDTO;
import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.modules.session.mapper.SessionInfoDrawMapper;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.constants.RedisConstant;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisUtil redisUtil;

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

    /**
     * 根据主键获取一行 缓存
     *
     * @param id
     * @return
     */
    @Override
    public SessionInfoDrawModel cacheGetById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        final String KEY = ModelUtil.modelMainKey(SessionInfoDrawModel.class) + RedisConstant.Keyword.ID + ":" + id;

        SessionInfoDrawModel obj = redisUtil.getCacheObject(KEY);
        if (Objects.isNull(obj)){
            SessionInfoDrawModel one = this.getById(id);
            if (Objects.nonNull(one)){
                redisUtil.setCacheObject(KEY,one,30L, TimeUnit.MINUTES);
                obj = one;
            }
        }
        return obj;
    }
}
