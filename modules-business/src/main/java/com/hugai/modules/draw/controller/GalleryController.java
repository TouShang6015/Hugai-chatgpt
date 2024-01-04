package com.hugai.modules.draw.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.Constants;
import com.hugai.common.modules.entity.draw.model.GalleryCommonModel;
import com.hugai.common.modules.entity.draw.vo.GalleryItemDataVO;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.draw.service.GalleryCommonService;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.core.function.OR;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/10/16 10:16
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.DRAW + "/gallery")
@Api(value = "画廊", tags = "画廊")
public class GalleryController {

    private final SessionRecordDrawService sessionRecordDrawService;

    private final SessionInfoDrawService sessionInfoDrawService;

    private final GalleryCommonService galleryCommonService;

    private final UserInfoService userInfoService;

    @ApiOperation(value = "设置图片公开")
    @PostMapping("/setDrawCommon")
    public Result setDrawCommon(@RequestBody GalleryCommonModel param) {
        GalleryCommonModel galleryCommonModel = galleryCommonService.setDrawCommon(param);
        return Result.success(galleryCommonModel);
    }

    @ApiOperation(value = "取消图片公开")
    @PostMapping("/cancelDrawCommon")
    public Result cancelDrawCommon(@RequestBody GalleryCommonModel param) {
        galleryCommonService.removeDrawCommon(param);
        return Result.success();
    }

    @PostMapping("/commonGallery")
    @ApiOperation(value = "公开画廊")
    public Result commonGallery(@RequestBody SessionRecordDrawModel param) {
        IPage<SessionRecordDrawModel> page = galleryCommonService.queryCommonSessionRecord(param);
        IPage<GalleryItemDataVO> resultData = PageUtil.convert(page, this::handlerRecordConvertGalleyShowData);
        return Result.success(resultData);
    }

    @PostMapping("/ownerGallery")
    @ApiOperation(value = "我的画廊(过滤mj的缩略图)")
    public Result getOwnerGallery(@RequestBody SessionRecordDrawModel param) {
        return getOwnerGallery(null, param);
    }

    @PostMapping("/ownerGallery/{userId}")
    @ApiOperation(value = "我的画廊")
    public Result getOwnerGallery(@PathVariable(required = false, value = "userId") Long userId, @RequestBody SessionRecordDrawModel param) {
        if (Objects.isNull(userId)) {
            userId = SecurityContextUtil.getUserId();
        }
        LambdaQueryWrapper<SessionRecordDrawModel> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(SessionRecordDrawModel::getUserId, userId)
                .orderByDesc(SessionRecordDrawModel::getCreateTime)
        ;
        IPage<SessionRecordDrawModel> page = sessionRecordDrawService.page(PageUtil.pageBean(param), wrapper);

        IPage<GalleryItemDataVO> resultData = PageUtil.convert(page, this::handlerRecordConvertGalleyShowData);

        OR.run(page.getRecords(), CollUtil::isNotEmpty, records -> {
            List<Long> sessionRecordDrawIds = records.stream().map(SessionRecordDrawModel::getId).distinct().collect(Collectors.toList());
            List<GalleryCommonModel> galleryCommonModels = OptionalUtil.ofNullList(
                    galleryCommonService.lambdaQuery().select(GalleryCommonModel::getId, GalleryCommonModel::getSessionRecordDrawId).in(GalleryCommonModel::getSessionRecordDrawId, sessionRecordDrawIds).list()
            );

            resultData.getRecords().forEach(item -> {
                GalleryCommonModel galleryCommonModel = galleryCommonModels.stream().filter(e -> e.getSessionRecordDrawId().equals(item.getId())).findFirst().orElse(null);
                item.setIfCommon(Constants.BOOLEAN.FALSE);
                if (Objects.nonNull(galleryCommonModel)) {
                    item.setIfCommon(Constants.BOOLEAN.TRUE);
                    item.setGalleryCommonId(galleryCommonModel.getId());
                }
            });

        });
        return Result.success(resultData);
    }

    /**
     * 转换画廊展示数据
     *
     * @param recordDrawList
     * @return
     */
    public List<GalleryItemDataVO> handlerRecordConvertGalleyShowData(List<SessionRecordDrawModel> recordDrawList) {
        if (CollUtil.isEmpty(recordDrawList))
            return CollUtil.newArrayList();

        List<Long> userIds = recordDrawList.stream().map(SessionRecordDrawModel::getUserId).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<UserInfoModel> userInfoList = OptionalUtil.ofNullList(userInfoService.lambdaQuery().in(UserInfoModel::getId, userIds).list());

        return recordDrawList.stream().map(item -> {
            Long userId = item.getUserId();
            UserInfoModel userInfoModel = userInfoList.stream().filter(userItem -> userItem.getId().equals(userId)).findFirst().orElseGet(UserInfoModel::new);
            GalleryItemDataVO showVO = new GalleryItemDataVO();
            showVO.setId(item.getId());
            showVO.setUserId(userId);
            showVO.setUserName(userInfoModel.getNickName());
            showVO.setUserImgUrl(userInfoModel.getImgUrl());
            showVO.setEmail(userInfoModel.getEmail());
            showVO.setSessionInfoDrawId(item.getSessionInfoDrawId());
            showVO.setSessionRecordDrawId(item.getId());
            showVO.setPrompt(item.getPrompt());
            showVO.setImgUrl(item.getDrawImgUrl());
            return showVO;
        }).collect(Collectors.toList());
    }

}
