package com.hugai.core.drawTask.strategy.impl.mj;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.hugai.common.enums.flow.DrawType;
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
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;

/**
 * @author WuHao
 * @since 2023/9/26 14:13
 */
@Slf4j
public class ApiStrategyMjU extends DrawMJAbstractStrategy<MjBaseRequest> {

    public ApiStrategyMjU(TaskDrawModel drawData) {
        super(drawData);
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
        String originalTaskId = apiRequestParam.getOriginalTaskId();
        Assert.notEmpty(originalTaskId, () -> new BusinessException("原始任务不能为空，或任务类型无效"));

        TaskDrawModel originalTaskDrawModel = taskDrawService.getById(originalTaskId);
        Assert.notNull(originalTaskDrawModel, () -> new BusinessException("任务类型无效"));
        Assert.isFalse(CollUtil.newArrayList(DrawType.ApiKey.mj_u.name()).contains(originalTaskDrawModel.getDrawApiKey()), () -> new BusinessException("任务类型无效"));

        Long sessionInfoDrawId = originalTaskDrawModel.getSessionInfoDrawId();
        SessionRecordDrawModel sessionRecordDrawModel = sessionRecordDrawService.lambdaQuery().eq(SessionRecordDrawModel::getSessionInfoDrawId, sessionInfoDrawId).one();
        Assert.notNull(sessionRecordDrawModel, () -> new BusinessException("任务类型无效"));

        String originalTaskRequestParam = sessionRecordDrawModel.getMjExtendParam();
        MessageResponseData messageResponseData = JSON.parseObject(originalTaskRequestParam, MessageResponseData.class);
        Assert.notNull(messageResponseData, () -> new BusinessException("任务类型无效"));
        Assert.notEmpty(messageResponseData.getMessageId(), () -> new BusinessException("任务类型无效"));
        Assert.notEmpty(messageResponseData.getMessageHash(), () -> new BusinessException("任务类型无效"));

        String taskId = this.drawData.getId().toString();

        // 添加任务至队列管理器
        DrawTaskMjQueueManager queueManager = SpringUtils.getBean(DrawTaskMjQueueManager.class);
        // 获取discord 连接池
        DiscordAccountCacheObj discordAccountCacheBean = DiscordSocketAccountPool.getOne();
        DiscordApiClient discordApiClient = DiscordApiClient.init(discordAccountCacheBean.getDiscordAccount());

        Tuple2<String, String> oneChannelConfig = discordAccountCacheBean.getOneChannelConfig();
        String guildId = oneChannelConfig.getT1();
        String channelId = oneChannelConfig.getT2();
        String apiParam = this.apiDefaultParamsMap.get("upscale")
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
