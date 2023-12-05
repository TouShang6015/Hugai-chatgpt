package com.hugai.core.drawTask.strategy.impl.mj;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.hugai.common.enums.MjParamUnique;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.core.drawTask.manager.queue.DrawTaskMjQueueManager;
import com.hugai.core.drawTask.strategy.DrawMJAbstractStrategy;
import com.hugai.core.midjourney.client.DiscordApiClient;
import com.hugai.core.midjourney.common.entity.TaskObj;
import com.hugai.core.midjourney.common.entity.request.MjBaseRequest;
import com.hugai.core.midjourney.common.entity.request.MjTxt2ImgRequest;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.manager.TaskQueueManager;
import com.hugai.core.midjourney.pool.DiscordAccountCacheObj;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;

/**
 * @author WuHao
 * @since 2023/9/26 14:13
 */
@Slf4j
public class ApiStrategyMjTxt2Img extends DrawMJAbstractStrategy<MjTxt2ImgRequest> {


    public ApiStrategyMjTxt2Img(CacheService cacheService, TaskDrawModel drawData) {
        super(cacheService, drawData);
    }

    /**
     * 获取绘图api唯一标识
     *
     * @return
     */
    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.mj_txt2img;
    }

    /**
     * 获取策略器映射aip映射实体Class
     *
     * @return
     */
    @Override
    protected Class<MjTxt2ImgRequest> getMappingCls() {
        return MjTxt2ImgRequest.class;
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

        String prompt = apiRequestParam.getPrompt();

        String taskId = this.drawData.getId().toString();

        // 添加任务至队列管理器
        DrawTaskMjQueueManager queueManager = SpringUtils.getBean(DrawTaskMjQueueManager.class);
        // 获取discord 连接池
        DiscordAccountCacheObj discordAccountCacheBean = DiscordSocketAccountPool.getOne();
        DiscordApiClient discordApiClient = DiscordApiClient.init(discordAccountCacheBean.getDiscordAccount());

        Tuple2<String, String> oneChannelConfig = discordAccountCacheBean.getOneChannelConfig();
        String guildId = oneChannelConfig.getT1();
        String channelId = oneChannelConfig.getT2();
        String apiParam = this.paramWebApi.cacheGetValueByKey(MjParamUnique.imagine).replace("$guild_id", guildId)
                .replace("$channel_id", channelId)
                .replace("$session_id", discordAccountCacheBean.getSessionId())
                .replace("$nonce", taskId)
                .replace("$prompt", prompt);

        // 添加任务队列
        TaskObj mjTaskObj = TaskObj.builder()
                .channelId(channelId)
                .guildId(guildId)
                .prompt(prompt.trim())
                .nonce(taskId)
                .drawApiKey(this.apiKey().name())
                .targetHandler(MjStrategyTypeEnum.Imagine.name())
                .build();
        TaskQueueManager.add(mjTaskObj);
        queueManager.start(taskId, () -> {
            discordApiClient.interactionsVoid(apiParam);
        });
    }

}
