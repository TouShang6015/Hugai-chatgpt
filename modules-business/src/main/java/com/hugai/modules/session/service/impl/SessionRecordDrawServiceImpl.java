package com.hugai.modules.session.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.modules.session.mapper.SessionRecordDrawMapper;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.constants.RedisConstant;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 会话详情 业务实现类
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Service
public class SessionRecordDrawServiceImpl extends ServiceImpl<SessionRecordDrawMapper, SessionRecordDrawModel> implements SessionRecordDrawService {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据会话主键获取列表
     *
     * @param sessionDrawInfoId
     * @return
     */
    @Override
    public List<SessionRecordDrawModel> cacheGetBySessionInfoId(Long sessionDrawInfoId) {
        if (Objects.isNull(sessionDrawInfoId)){
            return null;
        }
        final String KEY = ModelUtil.modelMainKey(SessionRecordDrawModel.class) + RedisConstant.Keyword.LIST_PARAM + ":" + sessionDrawInfoId;

        List<SessionRecordDrawModel> objList = redisUtil.getCacheList(KEY);
        if (CollUtil.isEmpty(objList)){
            List<SessionRecordDrawModel> modelList = this.lambdaQuery().eq(SessionRecordDrawModel::getSessionInfoDrawId, sessionDrawInfoId).list();
            if (CollUtil.isNotEmpty(modelList)){
                redisUtil.setCacheList(KEY,modelList);
                redisUtil.expire(KEY,30L, TimeUnit.MINUTES);
                objList = modelList;
            }
        }
        return objList;
    }
}
