package com.hugai.core.drawTask.manager.service;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.core.sd.entity.request.Img2ImgRequest;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.hugai.core.sd.entity.response.Img2ImgResponse;
import com.hugai.core.sd.entity.response.TxtImgResponse;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * sd绘图请求响应处理
 *
 * @author WuHao
 * @since 2023/10/1 16:34
 */
@Slf4j
@Component
public class DrawSdResponseService {

    @Resource
    private TaskDrawService taskDrawService;
    @Resource
    private FileServiceContext fileServiceContext;
    @Resource
    private SessionInfoDrawService sessionInfoDrawService;
    @Resource
    private SessionRecordDrawService sessionRecordDrawService;

    /**
     * 文生图响应处理
     *
     * @param taskId
     * @param res
     */
    @Transactional
    public void handleTxt2Img(String taskId, TxtImgRequest req, TxtImgResponse res) {
        Assert.notEmpty(taskId);
        Assert.notNull(req);
        Assert.notNull(res);

        try {
            TaskDrawModel taskDrawModel = taskDrawService.getById(taskId);

            final Long userId = taskDrawModel.getUserId();

            DrawPersistenceCollection COLLECTION = new DrawPersistenceCollection();

            final long sessionInfoDrawId = IdWorker.getId();

            OR.run(res, Objects::nonNull, response -> {
                log.info("[绘图 - sd] api 请求成功");

                // 创建会话实体
                SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                        .userId(userId)
                        .taskId(taskId)
                        .prompt(req.getPrompt())
                        .drawUniqueKey(DrawType.SD.getKey())
                        .drawApiKey(DrawType.ApiKey.sd_txt2img.name())
                        .sdResponseInfo(response.getInfo())
                        .build();
                sessionInfoSaveParam.setId(sessionInfoDrawId);
                COLLECTION.setSessionInfoDrawModelInsert(sessionInfoSaveParam);

                // 响应记录
                List<String> images = response.getImages();
                FileService fileService = fileServiceContext.getFileService();

                // 创建会话记录
                List<SessionRecordDrawModel> sessionRecordSaveParamList = images.stream().filter(Objects::nonNull).map(image -> {
                    // 图片存储至系统
                    AtomicReference<String> imgUrl = new AtomicReference<>();
                    OR.run(image, StrUtil::isNotEmpty, url -> {
                        try (InputStream inputStream = new ByteArrayInputStream(Base64Decoder.decode(image));) {
                            FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                            imgUrl.set(fileResponse.getFilePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            log.error("图片转存储失败：{}", JSON.toJSONString(image));
                        }
                    });

                    return SessionRecordDrawModel.builder()
                            .userId(userId)
                            .taskId(taskId)
                            .sessionInfoDrawId(sessionInfoDrawId)
                            .prompt(req.getPrompt())
                            .drawUniqueKey(DrawType.SD.getKey())
                            .drawApiKey(DrawType.ApiKey.sd_txt2img.name())
                            .drawImgUrl(imgUrl.get())
                            .build();
                }).collect(Collectors.toList());

                COLLECTION.setSessionRecordDrawModelListInsert(sessionRecordSaveParamList);

                String showImgUrl = sessionRecordSaveParamList.stream().findFirst().orElseGet(SessionRecordDrawModel::new).getDrawImgUrl();
                sessionInfoSaveParam.setShowImg(showImgUrl);

            });
            this.finish(taskId, COLLECTION);
        } catch (Exception e) {
            e.printStackTrace();
            taskDrawService.lambdaUpdate()
                    .set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey())
                    .eq(TaskDrawModel::getId, taskId).update();
        }

    }

    /**
     * 图生图相应处理
     *
     * @param taskId
     * @param req
     * @param res
     */
    @Transactional
    public void handleImg2Img(String taskId, Img2ImgRequest req, Img2ImgResponse res) {
        Assert.notEmpty(taskId);
        Assert.notNull(req);
        Assert.notNull(res);

        try {
            TaskDrawModel taskDrawModel = taskDrawService.getById(taskId);

            final Long userId = taskDrawModel.getUserId();

            DrawPersistenceCollection COLLECTION = new DrawPersistenceCollection();

            final long sessionInfoDrawId = IdWorker.getId();

            String firstImageBase64 = req.getInitImages().stream().findFirst().orElse(null);
            Assert.notEmpty(firstImageBase64,() -> new BusinessException("请求参数异常，无垫图数据"));

            OR.run(res, Objects::nonNull, response -> {
                log.info("[绘图 - sd] api 请求成功");

                FileService fileService = fileServiceContext.getFileService();

                // 创建会话实体
                SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                        .userId(userId)
                        .taskId(taskId)
                        .prompt(req.getPrompt())
                        .drawUniqueKey(DrawType.SD.getKey())
                        .drawApiKey(DrawType.ApiKey.sd_txt2img.name())
                        .sdResponseInfo(response.getInfo())
                        .build();
                sessionInfoSaveParam.setId(sessionInfoDrawId);
                try (InputStream inputStream = new ByteArrayInputStream(Base64Decoder.decode(firstImageBase64));) {
                    FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                    sessionInfoSaveParam.setBaseImg(fileResponse.getFilePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("图片转存储失败");
                }
                COLLECTION.setSessionInfoDrawModelInsert(sessionInfoSaveParam);

                // 响应记录
                List<String> images = response.getImages();

                // 创建会话记录
                List<SessionRecordDrawModel> sessionRecordSaveParamList = images.stream().filter(Objects::nonNull).map(image -> {
                    // 图片存储至系统
                    AtomicReference<String> imgUrl = new AtomicReference<>();
                    OR.run(image, StrUtil::isNotEmpty, url -> {
                        try (InputStream inputStream = new ByteArrayInputStream(Base64Decoder.decode(image));) {
                            FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                            imgUrl.set(fileResponse.getFilePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            log.error("图片转存储失败：{}", JSON.toJSONString(image));
                        }
                    });

                    return SessionRecordDrawModel.builder()
                            .userId(userId)
                            .taskId(taskId)
                            .sessionInfoDrawId(sessionInfoDrawId)
                            .prompt(req.getPrompt())
                            .drawUniqueKey(DrawType.SD.getKey())
                            .drawApiKey(DrawType.ApiKey.sd_txt2img.name())
                            .drawImgUrl(imgUrl.get())
                            .baseImg(sessionInfoSaveParam.getBaseImg())
                            .build();
                }).collect(Collectors.toList());

                COLLECTION.setSessionRecordDrawModelListInsert(sessionRecordSaveParamList);

                String showImgUrl = sessionRecordSaveParamList.stream().findFirst().orElseGet(SessionRecordDrawModel::new).getDrawImgUrl();
                sessionInfoSaveParam.setShowImg(showImgUrl);

            });
            this.finish(taskId, COLLECTION);
        } catch (Exception e) {
            e.printStackTrace();
            taskDrawService.lambdaUpdate()
                    .set(TaskDrawModel::getTaskStatus, TaskStatus.FAIL.getKey())
                    .eq(TaskDrawModel::getId, taskId).update();
        }

    }

    @Transactional
    public void finish(String taskId, DrawPersistenceCollection collection) {
        OR.run(collection.getSessionInfoDrawModelInsert(), Objects::nonNull, sessionInfoDrawService::save);
        OR.run(collection.getSessionRecordDrawModelListInsert(), CollUtil::isNotEmpty, sessionRecordDrawService::saveBatch);

        // 更新任务状态
        taskDrawService.lambdaUpdate()
                .set(TaskDrawModel::getTaskStatus, TaskStatus.SUCCESS.getKey())
                .set(TaskDrawModel::getSessionInfoDrawId, collection.getSessionInfoDrawModelInsert().getId())
                .set(TaskDrawModel::getTaskEndTime, DateUtils.nowDateFormat())
                .set(TaskDrawModel::getShowImg, collection.getSessionInfoDrawModelInsert().getShowImg())
                .eq(TaskDrawModel::getId, taskId).update();
    }

}
