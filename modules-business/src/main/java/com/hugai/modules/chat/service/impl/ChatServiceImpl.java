package com.hugai.modules.chat.service.impl;

import cn.hutool.core.lang.Assert;
import com.hugai.chatsdk.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.session.RecordData;
import com.hugai.chatsdk.service.ChatBusinessService;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.common.support.strategy.StrategyServiceContext;
import com.hugai.core.chat.account.service.SdkAccountBuildService;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.hugai.core.chat.handlers.ChatRequestHandler;
import com.hugai.modules.chat.service.ChatService;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天会话 业务实现
 *
 * @author WuHao
 * @since 2023/6/6 10:52
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private SessionRecordService sessionRecordService;
    @Resource
    private ChatRequestHandler chatRequestHandler;
    @Resource
    private IChatSdkService chatSdkService;
    @Resource
    private IChatModelService chatModelService;
    @Resource
    private IChatKeysService chatKeysService;
    @Resource
    private StrategyServiceContext<ChatBusinessService> chatBusinessContext;
    @Resource
    private StrategyServiceContext<SdkAccountBuildService> accountBuildServiceContext;

    /**
     * 发送消息
     *
     * @param param
     */
    @Transactional
    @Override
    public void sendChatMessage(ChatRequestParam param) {

        List<RecordData> recordList = chatRequestHandler.buildChatSdkParam(param);

        ChatModelModel chatModel = chatModelService.getById(param.getChatModelId());
        Assert.notNull(chatModel, () -> new BusinessException("未找到对话模型，请检查配置"));

        ChatSdkModel chatSdkModel = chatSdkService.getById(chatModel.getChatSdkId());
        Assert.notNull(chatModel, () -> new BusinessException("未找到对话SDK平台信息，请检查配置"));

        // 构建sdk账户参数
        SdkAccountBuildService accountBuildService = accountBuildServiceContext.getService(chatSdkModel.getUniqueKey(), () -> new BusinessException("对话SDK帐号构建参数失败"));
        ChatSdkAccount chatSdkAccount = accountBuildService.buildSdkAccount(chatModel, chatSdkModel, param);
        // 请求sdk对话
        ChatBusinessService service = chatBusinessContext.getService(chatSdkModel.getUniqueKey(), () -> {
            log.error("[ChatBusinessService] 获取对话模型失败");
            throw new BusinessException("系统未找到对应对话模型，请检查配置。");
        });
        ChatSdkStorageResponse response = service.ChatCompletionStream(recordList, chatSdkAccount);

        // 帐号异常停用key
        if (response.getAccountError()) {
            String apiToken = response.getAccount().getApiToken();
            chatKeysService.removeByApiToken(apiToken);
            return;
        }

        sessionRecordService.responseInsertHandle(recordList, param, response);
    }


}
