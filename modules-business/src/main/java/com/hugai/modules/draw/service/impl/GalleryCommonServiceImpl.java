package com.hugai.modules.draw.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.Constants;
import com.hugai.common.modules.entity.draw.model.GalleryCommonModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.draw.mapper.GalleryCommonMapper;
import com.hugai.modules.draw.service.GalleryCommonService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class GalleryCommonServiceImpl extends ServiceImpl<GalleryCommonMapper, GalleryCommonModel> implements GalleryCommonService {

    @Resource
    private SessionRecordDrawService sessionRecordDrawService;

    @Override
    public IPage<SessionRecordDrawModel> queryCommonSessionRecord(SessionRecordDrawModel param) {
        return baseMapper.queryCommonSessionRecord(PageUtil.pageBean(param), param);
    }

    @Transactional
    @Override
    public GalleryCommonModel setDrawCommon(GalleryCommonModel param) {
        Long userId = SecurityContextUtil.getUserId();

        Long sessionRecordDrawId = param.getSessionRecordDrawId();
        Assert.notNull(sessionRecordDrawId, () -> new BusinessException("绘图详情主键不能为空"));

        if (this.lambdaQuery()
                .eq(GalleryCommonModel::getSessionRecordDrawId, sessionRecordDrawId)
                .eq(GalleryCommonModel::getUserId, userId)
                .count() > 0) {
            throw new BusinessException("重复操作");
        }

        SessionRecordDrawModel recordDrawModel = sessionRecordDrawService.getById(sessionRecordDrawId);
        Assert.notNull(recordDrawModel, () -> new BusinessException("未找到图片信息"));

        Assert.isFalse(!userId.equals(recordDrawModel.getUserId()), () -> new BusinessException("操作失败，数据不匹配"));

        GalleryCommonModel saveModel = GalleryCommonModel.builder()
                .sessionRecordDrawId(recordDrawModel.getId())
                .sessionInfoDrawId(recordDrawModel.getSessionInfoDrawId())
                .userId(userId)
                .imgUrl(recordDrawModel.getDrawImgUrl())
                .ifShowAll(StrUtil.isEmpty(param.getIfShowAll()) ? Constants.BOOLEAN.FALSE : param.getIfShowAll())
                .prompt(recordDrawModel.getPrompt())
                .build();
        this.save(saveModel);
        return saveModel;
    }

    @Override
    public void removeDrawCommon(GalleryCommonModel param) {
        Long userId = SecurityContextUtil.getUserId();

        Long id = param.getId();
        Assert.notNull(id, () -> new BusinessException("主键不能为空"));

        OR.run(this.getById(id), Objects::nonNull, model -> {
            Assert.isFalse(!userId.equals(model.getUserId()), () -> new BusinessException("操作失败，数据不匹配"));
        });

        this.removeById(id);
    }

}
