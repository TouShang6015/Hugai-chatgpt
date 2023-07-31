package com.hugai.core.session.sessionType.strategy.domain;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.flow.DomainGroup;
import com.hugai.core.openai.api.CompletionOpenApi;
import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.hugai.core.openai.enums.RoleEnum;
import com.hugai.core.openai.utils.TokenCalculateUtil;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.sse.CacheSsePool;
import com.hugai.modules.session.entity.dto.SessionRecordDTO;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/6/19 17:13
 */
public class DomainDefaultImpl extends DomainSessionStrategyImpl {

    public DomainGroup getDomainGroup() {
        return DomainGroup.COMMON;
    }

    /**
     * 发送消息
     *
     * @param requestConsumer
     * @return
     */
    @Override
    public List<CompletionResponse> requestOpenApiCompletion(Consumer<CompletionRequest> requestConsumer) {

        SessionCacheData cacheData = this.getCacheData();
        String content = cacheData.getContent();
        Assert.notEmpty(content, () -> new BusinessException("发送内容不能为空"));

        SseEmitter sse = CacheSsePool.get(cacheData.getSseId());

        CompletionOpenApi openApi = SpringUtils.getBean(CompletionOpenApi.class);
        CompletionRequest completionRequest = this.openApiRequestBuild().get();
        requestConsumer.accept(completionRequest);

        return openApi.streamCompletion(() -> completionRequest, sse);
    }


    /**
     * openApi 接口请求参数构建
     *
     * @return
     */
    protected Supplier<CompletionRequest> openApiRequestBuild() {
        int onceToken = this.getDomainGroup().modulesToken().getOnce();

        List<SessionRecordModel> sessionValidRequestContext = OptionalUtil.ofNullList(this.getSessionValidRequestContext());

        SessionRecordModel recordDomainTop = sessionValidRequestContext.stream().filter(item -> Constants.BOOLEAN.TRUE.equals(item.getIfDomainTop())).findFirst().orElseGet(SessionRecordModel::new);

        List<SessionRecordModel> recordList = sessionValidRequestContext.stream().filter(item -> !Constants.BOOLEAN.TRUE.equals(item.getIfDomainTop())).collect(Collectors.toList());

        String content = recordList.stream().map(SessionRecordModel::getContent).filter(StrUtil::isNotEmpty).collect(Collectors.joining("/n"));

        content += ("/n" + this.getCacheData().getContent());

        String finalContent = recordDomainTop.getContent() + content;
        return () -> CompletionRequest.builder()
                .model(this.getUseModel())
                .prompt(finalContent)
                .maxTokens(onceToken)
                .n(1)
                .build();
    }

    /**
     * 新增会话时初始化一条会话信息
     *
     * @return
     */
    @Override
    public SessionRecordDTO addSessionInitFirstRecord() {
        this.setDomainModel();
        SessionCacheData cacheData = this.getCacheData();

        String[] contentSplit = cacheData.getContent().split(",");
        String content = String.format(this.domainModel.getAboveContent(), contentSplit);

        SessionRecordDTO dto = new SessionRecordDTO();
        dto.setUserId(cacheData.getUserId());
        dto.setSessionId(cacheData.getSessionId());
        dto.setDomainUniqueKey(cacheData.getDomainUniqueKey());
        dto.setRole(RoleEnum.system.name());
        dto.setContent(content);
        dto.setIfShow(Constants.BOOLEAN.FALSE);
        dto.setIfContext(Constants.BOOLEAN.TRUE);
        dto.setIfDomainTop(Constants.BOOLEAN.TRUE);
        dto.setConsumerToken(TokenCalculateUtil.getTokenNumOfContent(content));
        return dto;
    }
}
