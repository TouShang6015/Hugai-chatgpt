package com.hugai.core.drawTask.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.context.ChatBusinessServiceContext;
import com.hugai.chatsdk.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.session.RecordData;
import com.hugai.chatsdk.service.ChatBusinessService;
import com.hugai.common.constants.Constants;
import com.hugai.common.entity.baseResource.ResourceChatConfigVO;
import com.hugai.common.enums.ChatRole;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.chat.account.SdkAccountBuildContext;
import com.hugai.core.chat.account.service.SdkAccountBuildService;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkService;
import com.hugai.modules.draw.service.TaskDrawService;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 绘图策略器抽象层
 *
 * @author WuHao
 * @since 2023/9/8 13:13
 */
@Slf4j
public abstract class DrawAbstractStrategy<MappingCls> implements DrawApiService {

    protected DrawType drawType;

    protected DrawType.ApiKey apiKey;

    protected TaskDrawModel drawData;

    protected SessionCacheDrawData cacheData;

    protected SessionInfoDrawService sessionInfoDrawService;

    protected TaskDrawService taskDrawService;

    protected BaseResourceWebApi baseResourceWebApi;

    protected CacheService cacheService;

    protected SdkAccountBuildContext sdkAccountBuildContext;

    protected ChatBusinessServiceContext chatBusinessServiceContext;

    public DrawAbstractStrategy(CacheService cacheService, TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        Assert.notNull(drawData);
        this.drawData = drawData;
        this.apiKey = DrawType.ApiKey.getByName(drawData.getDrawApiKey());
        this.drawType = DrawType.getByApiKey(this.apiKey);
        this.cacheData = cacheData;
        this.sessionInfoDrawService = SpringUtils.getBean(SessionInfoDrawService.class);
        this.baseResourceWebApi = SpringUtils.getBean(BaseResourceWebApi.class);
        this.taskDrawService = SpringUtils.getBean(TaskDrawService.class);
        this.sdkAccountBuildContext = cacheService.getSdkAccountBuildContext();
        this.chatBusinessServiceContext = cacheService.getChatBusinessServiceContext();
        if (Objects.isNull(cacheData)) {
            OR.run(this.drawData.getRequestParam(), StrUtil::isNotEmpty, requestParamString -> {
                this.cacheData = JSON.parseObject(requestParamString, SessionCacheDrawData.class);
            });
        }
    }

    public DrawAbstractStrategy(CacheService cacheService, TaskDrawModel drawData) {
        this(cacheService, drawData, null);
    }

    /**
     * 请求外部接口返回持久化集合实体
     *
     * @return
     */
    @Override
    public void executeApi() {
        UserThreadLocal.set(this.drawData.getUserId());
        try {
            this.executeApiHandle();
        } finally {
            UserThreadLocal.remove();
        }
    }


    /**
     * 优化prompt
     *
     * @return
     */
    protected String optimizePrompt() {
        ResourceChatConfigVO resourceChatConfig = this.baseResourceWebApi.getResourceChatConfigVO();
        Long chatModelId = resourceChatConfig.getDrawPromptOptimizeChatModelId();

        ChatModelModel chatModel = SpringUtils.getBean(IChatModelService.class).getById(chatModelId);
        ChatSdkModel chatSdk = SpringUtils.getBean(IChatSdkService.class).getById(chatModel.getChatSdkId());

        String drawPromptOptimizeContent = resourceChatConfig.getDrawPromptOptimizeContent();
        AtomicReference<String> prompt = new AtomicReference<>(this.cacheData.getPrompt());

        OR.run(this.cacheData.getOptimizePrompt(), StrUtil::isNotEmpty, optimizePrompt -> {
            if (Constants.BOOLEAN.FALSE.equals(optimizePrompt)) {
                return;
            } else {
                try {
                    log.info("[绘图 - prompt参数优化]，原始prompt：{}", prompt.get());

                    SdkAccountBuildService accountBuildService = sdkAccountBuildContext.getService(chatSdk.getUniqueKey(), () -> new BusinessException("对话SDK帐号构建参数失败"));
                    ChatSdkAccount chatSdkAccount = accountBuildService.buildSdkAccount(chatModel, chatSdk, null);

                    ChatBusinessService chatBusinessService = chatBusinessServiceContext.getService(chatSdkAccount.getSdkUniqueKey(), () -> new BusinessException("系统未找到对应对话模型"));

                    ChatSdkStorageResponse response = chatBusinessService.chatCompletion(CollUtil.newArrayList(RecordData.builder().role(ChatRole.user.name()).content(drawPromptOptimizeContent + prompt.get()).build()), chatSdkAccount);

                    String content = response.getResponseRecordData().stream().findFirst().orElseGet(RecordData::new).getContent();
                    prompt.set(content);
                    log.info("[绘图 - prompt参数优化]，GPT 优化后的prompt：{}", prompt.get());
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                    throw new BusinessException();
                }
            }
        });
        return prompt.get();
    }

    /**
     * executeApi 策略器完整处理
     *
     * @return
     */
    protected abstract void executeApiHandle();

    /**
     * 获取策略器映射aip映射实体Class
     *
     * @return
     */
    protected abstract Class<MappingCls> getMappingCls();

    /**
     * 释放线程变量
     */
    protected void close() {
        UserThreadLocal.remove();
    }

}
