package com.hugai.core.session.sessionType.strategy.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.hugai.common.enums.flow.DomainGroup;
import com.hugai.common.enums.flow.SessionType;
import com.hugai.core.openai.api.ChatOpenApi;
import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.sessionType.manager.SessionStrategyManager;
import com.hugai.core.session.sessionType.service.BusinessDomainService;
import com.hugai.modules.session.entity.convert.SessionRecordConvert;
import com.hugai.modules.session.entity.model.DomainModel;
import com.hugai.modules.session.service.DomainService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 领域会话类型策略实现
 *
 * @author WuHao
 * @since 2023/6/19 13:43
 */
public class DomainSessionStrategyImpl extends SessionStrategyManager implements BusinessDomainService {

    protected DomainModel domainModel;

    /**
     * 获取领域会话配置上文
     */
    protected void setDomainModel() {
        if (Objects.isNull(this.domainModel)) {
            String domainUniqueKey = this.getCacheData().getDomainUniqueKey();
            Assert.notNull(domainUniqueKey, () -> new BusinessException("领域会话处理失败，缺少必要参数"));

            DomainModel domainModel = SpringUtils.getBean(DomainService.class).lambdaQuery().eq(DomainModel::getUniqueKey, domainUniqueKey).one();
            Assert.notNull(domainModel, () -> new BusinessException("未找到指定的会话领域类型，请检查配置"));

            this.domainModel = domainModel;
        }
    }

    /**
     * 获取领域类型服务实现
     *
     * @param <Service>
     * @return
     */
    public <Service extends DomainSessionStrategyImpl> Service getDomainService() {
        this.setDomainModel();
        List<DomainSessionStrategyImpl> services = CollUtil.newArrayList(
                new DomainDefaultImpl()
        );
        return (Service) services.stream().filter(item -> item.getDomainGroup().getKey().equals(this.domainModel.getDomainGroup())).findFirst().orElseThrow(() -> new BusinessException("未找到领域会话策略"));
    }

    /**
     * sessionType标识
     *
     * @return
     */
    @Override
    public SessionType sessionTypeSign() {
        return SessionType.DOMAIN;
    }

    /**
     * 发送消息
     *
     * @return
     */
    @Override
    public List<CompletionResponse> requestOpenApiCompletion(Consumer<CompletionRequest> requestConsumer) {
        return this.getDomainService().requestOpenApiCompletion(requestConsumer);
    }

    /**
     * 发送消息 聊天/文本
     *
     * @param requestConsumer
     * @return
     */
    @Override
    public List<ChatResponse> requestOpenApiChat(Consumer<ChatCompletionRequest> requestConsumer) {
        SessionCacheData cacheData = this.getCacheData();
        String content = cacheData.getContent();
        Assert.notEmpty(content, () -> new BusinessException("发送内容不能为空"));

        ChatOpenApi chatOpenApi = SpringUtils.getBean(ChatOpenApi.class);

        ChatCompletionRequest chatCompletionRequest = this.openApiChatRequestBuild().get();
        requestConsumer.accept(chatCompletionRequest);

        return chatOpenApi.streamChat(() -> chatCompletionRequest, cacheData.getConnectId());
    }

    /**
     * openApi chat接口请求参数构建
     *
     * @return
     */
    protected Supplier<ChatCompletionRequest> openApiChatRequestBuild() {
        int onceToken = this.getDomainGroup().modulesToken().getOnce();

        List<ChatMessage> chatMessages = SessionRecordConvert.INSTANCE.convertChatMessage(this.getSessionValidRequestContext());
        OptionalUtil.ofNullList(chatMessages).add(new ChatMessage(ChatMessageRole.USER.value(), this.getCacheData().getContent()));

        return () -> ChatCompletionRequest.builder()
                .model(this.getUseModel())
                .messages(chatMessages)
                .n(1)
                .maxTokens(onceToken)
                .logitBias(new HashMap<>())
                .build();
    }

    /**
     * 获取领域分组
     *
     * @return
     */
    protected DomainGroup getDomainGroup() {
        return DomainGroup.COMMON;
    }

    /**
     * 获取领域配置数据
     *
     * @return
     */
    @Override
    public DomainModel getDomainModel() {
        this.setDomainModel();
        return this.domainModel;
    }
}
