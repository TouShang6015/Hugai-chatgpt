package com.hugai.core.drawTask.strategy.impl.mj;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.common.enums.MjParamUnique;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.core.drawTask.manager.queue.DrawTaskMjQueueManager;
import com.hugai.core.drawTask.strategy.DrawMJAbstractStrategy;
import com.hugai.core.midjourney.client.DiscordApiClient;
import com.hugai.core.midjourney.common.entity.MessageResponseData;
import com.hugai.core.midjourney.common.entity.TaskObj;
import com.hugai.core.midjourney.common.entity.request.MjBaseRequest;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.manager.TaskQueueManager;
import com.hugai.core.midjourney.pool.DiscordAccountCacheObj;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;

import java.util.Date;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/9/26 14:13
 */
@Slf4j
public class ApiStrategyMjU extends DrawMJAbstractStrategy<MjBaseRequest> {

    public ApiStrategyMjU(CacheService cacheService, TaskDrawModel drawData) {
        super(cacheService, drawData);
    }

    /**
     * 获取绘图api唯一标识
     *
     * @return
     */
    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.mj_u;
    }

    /**
     * 获取策略器映射aip映射实体Class
     *
     * @return
     */
    @Override
    protected Class<MjBaseRequest> getMappingCls() {
        return MjBaseRequest.class;
    }

    /**
     * executeApi 策略器完整处理
     *
     * @return
     */
    @Override
    protected void mjApiExecute() {
        String requestParam = this.drawData.getRequestParam();
        MjBaseRequest apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls(), JSONReader.Feature.SupportSmartMatch);

        // 参数校验
        String originalTaskId = apiRequestParam.getOriginalTaskDrawId();
        Assert.notEmpty(originalTaskId, () -> new BusinessException("原始任务不能为空，或任务类型无效"));

        TaskDrawModel originalTaskDrawModel = taskDrawService.getById(originalTaskId);
        Assert.notNull(originalTaskDrawModel, () -> new BusinessException("任务类型无效"));
        Assert.isFalse(CollUtil.newArrayList(DrawType.ApiKey.mj_u.name()).contains(originalTaskDrawModel.getDrawApiKey()), () -> new BusinessException("任务类型无效"));

        Long sessionInfoDrawId = originalTaskDrawModel.getSessionInfoDrawId();
        SessionRecordDrawModel sessionRecordDrawModel = OptionalUtil.listFindFirst(sessionRecordDrawService.lambdaQuery().eq(SessionRecordDrawModel::getSessionInfoDrawId, sessionInfoDrawId).list());
        Assert.notNull(sessionRecordDrawModel, () -> new BusinessException("任务类型无效"));

        String originalTaskRequestParam = sessionRecordDrawModel.getMjExtendParam();
        MessageResponseData messageResponseData = JSON.parseObject(originalTaskRequestParam, MessageResponseData.class);
        Assert.notNull(messageResponseData, () -> new BusinessException("任务类型无效"));
        Assert.notEmpty(messageResponseData.getMessageId(), () -> new BusinessException("任务类型无效"));
        Assert.notEmpty(messageResponseData.getMessageHash(), () -> new BusinessException("任务类型无效"));

        String taskId = this.drawData.getId().toString();

        // 已经出图了则复制记录
        int index = apiRequestParam.getIndex();
        String prompt = sessionRecordDrawModel.getPrompt();
        SessionRecordDrawModel indexRecordDrawModel = OptionalUtil.listFindFirst(
                sessionRecordDrawService.lambdaQuery()
                        .eq(SessionRecordDrawModel::getUserId, this.drawData.getUserId())
                        .eq(SessionRecordDrawModel::getDrawApiKey, DrawType.ApiKey.mj_u.name())
                        .eq(SessionRecordDrawModel::getPrompt, prompt)
                        .eq(SessionRecordDrawModel::getMjImageIndex, index)
                        .eq(SessionRecordDrawModel::getOriginalTaskDrawId, originalTaskId)
                        .list()
        );

        // 任务队列管理器
        DrawTaskMjQueueManager queueManager = SpringUtils.getBean(DrawTaskMjQueueManager.class);

        if (Objects.nonNull(indexRecordDrawModel)) {
            SessionInfoDrawModel indexDrawModel = sessionInfoDrawService.getById(indexRecordDrawModel.getSessionInfoDrawId());
            TaskDrawModel indexTaskDrawModel = taskDrawService.getById(indexRecordDrawModel.getTaskId());
            Assert.notNull(indexDrawModel, () -> new BusinessException("数据异常"));
            Assert.notNull(indexTaskDrawModel, () -> new BusinessException("任务数据异常"));

            long copyDrawId = IdWorker.getId();
            SessionInfoDrawModel copyDrawModel = JSON.parseObject(JSON.toJSONString(indexDrawModel), SessionInfoDrawModel.class);
            copyDrawModel.setId(copyDrawId);
            copyDrawModel.setCreateTime(null);
            copyDrawModel.setUpdateTime(null);
            copyDrawModel.setTaskId(taskId);

            SessionRecordDrawModel copyRecordDrawModel = JSON.parseObject(JSON.toJSONString(indexRecordDrawModel), SessionRecordDrawModel.class);
            copyRecordDrawModel.setId(null);
            copyRecordDrawModel.setCreateTime(null);
            copyRecordDrawModel.setUpdateTime(null);
            copyRecordDrawModel.setTaskId(taskId);
            copyRecordDrawModel.setSessionInfoDrawId(copyDrawId);

            TaskDrawModel copyTaskDrawModel = JSON.parseObject(JSON.toJSONString(indexTaskDrawModel), TaskDrawModel.class);
            copyTaskDrawModel.setId(Long.valueOf(taskId));
            copyTaskDrawModel.setCreateTime(null);
            copyTaskDrawModel.setUpdateTime(null);
            copyTaskDrawModel.setSessionInfoDrawId(copyDrawId);
            copyTaskDrawModel.setTaskEndTime(new Date());

            queueManager.startSync(taskId, () -> {
                sessionInfoDrawService.save(copyDrawModel);
                sessionRecordDrawService.save(copyRecordDrawModel);
                taskDrawService.updateById(copyTaskDrawModel);
            });
        } else {
            // 获取discord 连接池
            DiscordAccountCacheObj discordAccountCacheBean = DiscordSocketAccountPool.getByChannelId(originalTaskDrawModel.getMjChannelId());
            Assert.notNull(discordAccountCacheBean, () -> new BusinessException("未找到此图片MJ服务帐号信息"));
            DiscordApiClient discordApiClient = DiscordApiClient.init(discordAccountCacheBean.getDiscordAccount());

            Tuple2<String, String> oneChannelConfig = discordAccountCacheBean.getOneChannelConfig();
            String guildId = oneChannelConfig.getT1();
            String channelId = oneChannelConfig.getT2();
            String apiParam = this.paramWebApi.cacheGetValueByKey(MjParamUnique.upscale)
                    .replace("$guild_id", guildId)
                    .replace("$channel_id", channelId)
                    .replace("$session_id", discordAccountCacheBean.getSessionId())
                    .replace("$nonce", taskId)
                    .replace("$message_id", messageResponseData.getMessageId())
                    .replace("$index", String.valueOf(apiRequestParam.getIndex()))
                    .replace("$message_hash", messageResponseData.getMessageHash());

            // 添加任务队列
            TaskObj mjTaskObj = TaskObj.builder()
                    .channelId(channelId)
                    .guildId(guildId)
                    .prompt(sessionRecordDrawModel.getPrompt())
                    .nonce(taskId)
                    .drawApiKey(this.apiKey().name())
                    .targetHandler(MjStrategyTypeEnum.UpscaleSuccess.name())
                    .index(apiRequestParam.getIndex())
                    .build();
            TaskQueueManager.add(mjTaskObj);
            queueManager.start(taskId, () -> {
                discordApiClient.interactionsVoid(apiParam);
            });
        }

    }

}
