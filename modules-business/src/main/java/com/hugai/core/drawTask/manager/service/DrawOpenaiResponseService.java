package com.hugai.core.drawTask.manager.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.common.modules.entity.draw.vo.DrawPersistenceCollection;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.utils.DateUtils;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/10/1 17:16
 */
@Slf4j
@Component
public class DrawOpenaiResponseService {

    @Resource
    private TaskDrawService taskDrawService;
    @Resource
    private FileServiceContext fileServiceContext;
    @Resource
    private SessionInfoDrawService sessionInfoDrawService;
    @Resource
    private SessionRecordDrawService sessionRecordDrawService;

    @Transactional
    public void handleTxt2img(String taskId, CreateImageRequest req, ImageResult res) {
        Assert.notEmpty(taskId);
        Assert.notNull(req);
        Assert.notNull(res);

        TaskDrawModel taskDrawModel = taskDrawService.getById(taskId);

        final Long userId = taskDrawModel.getUserId();

        DrawPersistenceCollection COLLECTION = new DrawPersistenceCollection();

        final long sessionInfoDrawId = IdWorker.getId();

        final String prompt = req.getPrompt();

        // 创建会话实体
        SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                .taskId(taskId)
                .userId(userId)
                .prompt(prompt)
                .drawUniqueKey(DrawType.OPENAI.getKey())
                .drawApiKey(DrawType.ApiKey.openai_txt2img.name())
                .build();
        sessionInfoSaveParam.setId(sessionInfoDrawId);
        COLLECTION.setSessionInfoDrawModelInsert(sessionInfoSaveParam);

        // 响应记录
        List<Image> images = res.getData();
        FileService fileService = fileServiceContext.getFileService();

        // 创建会话记录
        List<SessionRecordDrawModel> sessionRecordSaveParamList = images.stream().filter(Objects::nonNull).map(image -> {
            // 图片存储至系统
            AtomicReference<String> imgUrl = new AtomicReference<>();
            OR.run(image.getUrl(), StrUtil::isNotEmpty, url -> {
                try (InputStream inputStream = HttpUtil.createGet(url).execute().bodyStream();) {
                    FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                    imgUrl.set(fileResponse.getFilePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("图片转存储失败：{}", JSON.toJSONString(image));
                }
            });

            return SessionRecordDrawModel.builder()
                    .taskId(taskId)
                    .userId(userId)
                    .sessionInfoDrawId(sessionInfoDrawId)
                    .prompt(prompt)
                    .drawUniqueKey(DrawType.OPENAI.getKey())
                    .drawApiKey(DrawType.ApiKey.openai_txt2img.name())
                    .drawImgUrl(imgUrl.get())
                    .build();
        }).collect(Collectors.toList());

        COLLECTION.setSessionRecordDrawModelListInsert(sessionRecordSaveParamList);

        String showImgUrl = sessionRecordSaveParamList.stream().findFirst().orElseGet(SessionRecordDrawModel::new).getDrawImgUrl();
        sessionInfoSaveParam.setShowImg(showImgUrl);

        this.finish(taskId, COLLECTION);
    }

    /**
     * 图生图响应数据处理
     *
     * @param taskId
     * @param req
     * @param res
     */
    public void handleImg2Img(String taskId, CreateImageEditRequest req, ImageResult res) {
        Assert.notEmpty(taskId);
        Assert.notNull(req);
        Assert.notNull(res);

        TaskDrawModel taskDrawModel = taskDrawService.getById(taskId);

        final Long userId = taskDrawModel.getUserId();

        DrawPersistenceCollection COLLECTION = new DrawPersistenceCollection();

        final long sessionInfoDrawId = IdWorker.getId();

        final String prompt = req.getPrompt();

        // 创建会话实体
        SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                .userId(userId)
                .taskId(taskId)
                .prompt(prompt)
                .drawUniqueKey(DrawType.OPENAI.getKey())
                .drawApiKey(DrawType.ApiKey.openai_img2img.name())
                .build();
        sessionInfoSaveParam.setId(sessionInfoDrawId);
        COLLECTION.setSessionInfoDrawModelInsert(sessionInfoSaveParam);

        // 响应记录
        List<Image> images = res.getData();
        FileService fileService = fileServiceContext.getFileService();

        // 创建会话记录
        List<SessionRecordDrawModel> sessionRecordSaveParamList = images.stream().filter(Objects::nonNull).map(image -> {
            // 图片存储至系统
            AtomicReference<String> imgUrl = new AtomicReference<>();
            OR.run(image.getUrl(), StrUtil::isNotEmpty, url -> {
                try (InputStream inputStream = HttpUtil.createGet(url).execute().bodyStream();) {
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
                    .sessionInfoDrawId(sessionInfoSaveParam.getId())
                    .prompt(prompt)
                    .drawUniqueKey(DrawType.OPENAI.getKey())
                    .drawApiKey(DrawType.ApiKey.openai_img2img.name())
                    .drawImgUrl(imgUrl.get())
                    .build();
        }).collect(Collectors.toList());

        COLLECTION.setSessionRecordDrawModelListInsert(sessionRecordSaveParamList);

        String showImgUrl = sessionRecordSaveParamList.stream().findFirst().orElseGet(SessionRecordDrawModel::new).getDrawImgUrl();
        sessionInfoSaveParam.setShowImg(showImgUrl);

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
