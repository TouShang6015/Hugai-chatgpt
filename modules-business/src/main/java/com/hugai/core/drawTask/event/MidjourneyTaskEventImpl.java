package com.hugai.core.drawTask.event;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.common.enums.flow.TaskStatus;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.drawTask.manager.queue.DrawTaskMjQueueManager;
import com.hugai.core.midjourney.common.entity.TaskObj;
import com.hugai.core.midjourney.common.entity.request.MjBaseRequest;
import com.hugai.core.midjourney.manager.TaskQueueManager;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/9/27 14:46
 */
@Slf4j
@Component
public class MidjourneyTaskEventImpl implements MidjourneyTaskEventListener {

    @Resource
    private SessionInfoDrawService drawService;
    @Resource
    private SessionRecordDrawService recordDrawService;
    @Resource
    private TaskDrawService taskDrawService;
    @Resource
    private FileServiceContext fileServiceContext;
    @Resource
    private BaseResourceWebApi baseResourceWebApi;
    @Resource
    private DrawTaskMjQueueManager queueManager;

    /**
     * 执行任务
     *
     * @param taskObj
     */
    @Transactional
    @Override
    public void taskRun(TaskObj taskObj) {
        if (Objects.isNull(taskObj))
            return;
        log.info("[Task Run] 接收参数:{}", JSON.toJSONString(taskObj));

        try {
            this.functionImagine(taskObj);
        } finally {
            queueManager.awake(taskObj.getNonce(), () -> {
            });
            this.endTask(taskObj);
        }
    }

    /**
     * 更新任务
     *
     * @param taskId
     * @param id
     */
    @Override
    public void updateTask(String taskId, String id, String applicationId, String guildId, String channelId) {
        if (StrUtil.isEmpty(taskId) || StrUtil.isEmpty(id) || StrUtil.isEmpty(applicationId) || StrUtil.isEmpty(channelId)) {
            return;
        }

        // 更新任务状态
        OR.run(TaskQueueManager.get(taskId), Objects::nonNull, taskObj -> {
            OR.run(id, StrUtil::isNotEmpty, taskObj::setId);
            OR.run(applicationId, StrUtil::isNotEmpty, taskObj::setApplicationId);
            OR.run(guildId, StrUtil::isNotEmpty, taskObj::setGuildId);
            OR.run(channelId, StrUtil::isNotEmpty, taskObj::setChannelId);
            log.info("[Discord Task update] 任务更新 - TaskId: {} , id: {}", taskId, id);
        });

    }

    @Override
    public void startTask(String id, String finalPrompt) {
        if (StrUtil.isEmpty(id) || StrUtil.isEmpty(finalPrompt)) {
            return;
        }

        TaskObj taskObj = TaskQueueManager.getById(id);
        if (Objects.isNull(taskObj))
            return;
        String taskId = taskObj.getNonce();
        // 更新任务状态
        taskObj.setPrompt(finalPrompt);

        OR.run(taskDrawService.getById(taskId), Objects::nonNull, taskModel -> {
            String requestParam = taskModel.getRequestParam();
            MjBaseRequest requestParamObj = JSON.parseObject(requestParam, MjBaseRequest.class);
            requestParamObj.setPrompt(finalPrompt);
            taskDrawService.lambdaUpdate()
                    .set(TaskDrawModel::getTaskStatus, TaskStatus.RUNNING.getKey())
                    .set(TaskDrawModel::getRequestParam, JSON.toJSONString(requestParamObj))
                    .set(TaskDrawModel::getMjApplicationId, taskObj.getApplicationId())
                    .set(TaskDrawModel::getMjGuildId, taskObj.getGuildId())
                    .set(TaskDrawModel::getMjChannelId, taskObj.getChannelId())
                    .eq(TaskDrawModel::getId, taskId).update();
        });

        log.info("[Discord Task update] 任务开始 - TaskId: {} , id: {}", taskId, id);
    }

    @Override
    public void endTask(TaskObj taskQueueBean) {
        OR.run(taskQueueBean, Objects::nonNull, obj -> {
            SpringUtils.getBean(DrawTaskMjQueueManager.class).overQueue(obj.getNonce());
        });
        TaskQueueManager.remove(taskQueueBean);
    }

    /**
     * 结束任务
     *
     * @param taskId
     */
    @Transactional
    @Override
    public void errorTask(String taskId, String error) {
        try {
            if (StrUtil.isEmpty(taskId))
                return;
            TaskDrawModel taskDrawModel = taskDrawService.getById(taskId);
            if (Objects.isNull(taskDrawModel))
                return;
            taskDrawService.failTask(taskId, wrapper -> {
                wrapper.set(TaskDrawModel::getRemark, error);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OR.run(TaskQueueManager.get(taskId), Objects::nonNull, this::endTask);
            SpringUtils.getBean(DrawTaskMjQueueManager.class).overQueue(taskId);
        }
    }

    @Transactional
    public void functionImagine(TaskObj taskObj) {
        final String taskId = taskObj.getNonce();
        if (StrUtil.isEmpty(taskId))
            return;
        TaskDrawModel taskDrawModel = taskDrawService.getById(taskId);
        if (Objects.isNull(taskDrawModel))
            return;
        Long userId = taskDrawModel.getUserId();
        try {
            String originalImgUrl = taskObj.getResponseData().getImgUrl();
            String showImgUrl = "";
            // 下载图片
            FileService fileService = fileServiceContext.getFileService();
            ResourceMainVO resourceMain = baseResourceWebApi.getResourceMain();
            try (InputStream inputStream = HttpUtil.createGet(originalImgUrl)
                    .setHttpProxy(resourceMain.getProxyHost(), resourceMain.getProxyPort())
                    .timeout(1000 * 60)
                    .execute()
                    .bodyStream();) {
                FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                showImgUrl = fileResponse.getFilePath();
            } catch (IOException e) {
                log.error("图片转存储失败 - {} | imgUrl: {}", DrawType.MJ.getKey(), originalImgUrl);
                throw e;
            }
            // 构建绘图会话model
            final long sessionInfoDrawId = IdWorker.getId();
            final long sessionRecordDrawId = IdWorker.getId();

            SessionInfoDrawModel sessionInfoDrawModel = SessionInfoDrawModel.builder()
                    .userId(userId)
                    .taskId(taskId)
                    .prompt(taskObj.getPrompt())
                    .drawUniqueKey(DrawType.MJ.getKey())
                    .drawApiKey(taskObj.getDrawApiKey())
                    .showImg(showImgUrl)
                    .originalImgUrl(originalImgUrl)
                    .originalTaskDrawId(taskDrawModel.getOriginalTaskDrawId())
                    .build();
            sessionInfoDrawModel.setId(sessionInfoDrawId);

            SessionRecordDrawModel sessionRecordDrawModel = SessionRecordDrawModel.builder()
                    .sessionInfoDrawId(sessionInfoDrawId)
                    .userId(userId)
                    .taskId(taskId)
                    .drawUniqueKey(DrawType.MJ.getKey())
                    .drawApiKey(taskObj.getDrawApiKey())
                    .drawImgUrl(showImgUrl)
                    .prompt(taskObj.getPrompt())
                    .originalImgUrl(originalImgUrl)
                    .mjExtendParam(JSON.toJSONString(taskObj.getResponseData()))
                    .mjImageIndex(taskObj.getIndex())
                    .mjApplicationId(taskDrawModel.getMjApplicationId())
                    .mjGuildId(taskDrawModel.getMjGuildId())
                    .mjChannelId(taskDrawModel.getMjChannelId())
                    .originalTaskDrawId(taskDrawModel.getOriginalTaskDrawId())
                    .build();
            sessionRecordDrawModel.setId(sessionRecordDrawId);

            // 更新状态
            drawService.save(sessionInfoDrawModel);
            recordDrawService.save(sessionRecordDrawModel);
            taskDrawService.lambdaUpdate()
                    .set(TaskDrawModel::getTaskStatus, TaskStatus.SUCCESS.getKey())
                    .set(TaskDrawModel::getSessionInfoDrawId, sessionInfoDrawId)
                    .set(TaskDrawModel::getTaskEndTime, DateUtils.nowDateFormat())
                    .set(TaskDrawModel::getShowImg, showImgUrl)
                    .eq(TaskDrawModel::getId, taskDrawModel.getId())
                    .update();
        } catch (Exception e) {
            e.printStackTrace();
            taskDrawService.failTask(String.valueOf(taskDrawModel.getId()), wrapper -> wrapper.set(TaskDrawModel::getRemark, e.getMessage()));
        }

    }

}
