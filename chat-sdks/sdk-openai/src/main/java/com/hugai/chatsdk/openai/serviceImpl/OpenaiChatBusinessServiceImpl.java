package com.hugai.chatsdk.openai.serviceImpl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.handler.MessageSendHandler;
import com.hugai.chatsdk.openai.client.OpenaiClientFactory;
import com.hugai.chatsdk.common.service.ChatBusinessService;
import com.hugai.chatsdk.common.service.ChatResponseSyncDataService;
import com.hugai.common.enums.flow.ChatSdkType;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/11/28 9:56
 */
@Slf4j
@Service
public class OpenaiChatBusinessServiceImpl implements ChatBusinessService<ChatCompletionRequest> {

    @Resource
    private ChatResponseSyncDataService chatResponseSyncDataService;

    @Override
    public ChatSdkType type() {
        return ChatSdkType.openai;
    }

    @Override
    public ChatSdkStorageResponse chatCompletionStream(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {

        String connectId = chatSdkAccount.getConnectId();

        log.info("[平台-{}]  流式请求参数：{}", chatSdkAccount.getSdkUniqueKey(), JSON.toJSONString(recordList));

        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);

        ChatSdkStorageResponse response = new ChatSdkStorageResponse();
        response.setAccount(chatSdkAccount);

        try {
            OpenAiService service = OpenaiClientFactory.getService(chatSdkAccount);
            ChatCompletionRequest chatCompletionRequest = this.convertRequest(recordList, chatSdkAccount, e -> e.setStream(true));

            RecordData recordData = response.pushData(new RecordData());
            try {
                service.streamChatCompletion(chatCompletionRequest)
                        .blockingForEach(chunk -> {
                            List<ChatCompletionChoice> choices = chunk.getChoices();

                            Optional.ofNullable(choices).orElseGet(ArrayList::new).forEach(res -> {
                                ChatMessage message = Optional.ofNullable(res.getMessage()).orElseGet(ChatMessage::new);
                                OR.run(message.getRole(), StrUtil::isNotEmpty, recordData::setRole);
                                String resContent = Optional.ofNullable(message.getContent()).orElse("");
                                recordData.getContentSB().append(resContent);
                                // 发送消息
                                messageSendHandler.queueAdd(resContent);
                            });
                        });
            } catch (BusinessException e) {
                if (e.getCode() == HttpStatus.HTTP_OK) {
                    return response;
                }
                throw e;
            } finally {
                recordData.setContent(recordData.getContentSB().toString());
                service.shutdownExecutor();
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
            if (throwable.getCause() instanceof ConnectException) {
                throw new BusinessException("服务器节点异常，请稍后重试或联系管理员");
            }
            if (throwable instanceof OpenAiHttpException e) {
                int statusCode = e.statusCode;
                String code = e.code;
                List<Integer> accountErrorCode = CollUtil.newArrayList(HttpStatus.HTTP_UNAUTHORIZED, 429);
                if (accountErrorCode.contains(statusCode) || "insufficient_quota".equals(code)) {
                    response.setAccountError(true);
                }
                return response;
            }
            throw new BusinessException(throwable.getMessage());
        } finally {
            messageSendHandler.close();
        }

        chatResponseSyncDataService.syncChatResponse(chatSdkAccount, response, recordList);

        return response;
    }

    @Override
    public ChatSdkStorageResponse chatCompletion(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {
        String connectId = chatSdkAccount.getConnectId();

        log.info("[平台-{}]  非流式请求参数：{}", chatSdkAccount.getSdkUniqueKey(), JSON.toJSONString(recordList));

        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);

        ChatSdkStorageResponse response = new ChatSdkStorageResponse();

        try {
            OpenAiService service = OpenaiClientFactory.getService(chatSdkAccount);
            ChatCompletionRequest chatCompletionRequest = this.convertRequest(recordList, chatSdkAccount, e -> e.setStream(true));

            RecordData recordData = response.pushData(new RecordData());
            try {
                chatCompletionRequest.setStream(false);
                ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);

                Optional.ofNullable(chatCompletion.getChoices()).orElseGet(ArrayList::new).forEach(res -> {
                    ChatMessage message = Optional.ofNullable(res.getMessage()).orElseGet(ChatMessage::new);
                    OR.run(message.getRole(), StrUtil::isNotEmpty, recordData::setRole);
                    String resContent = Optional.ofNullable(message.getContent()).orElse("");
                    recordData.getContentSB().append(resContent);
                    // 发送消息
                    messageSendHandler.queueAdd(resContent);
                });
            } catch (BusinessException e) {
                if (e.getCode() == HttpStatus.HTTP_OK) {
                    return response;
                }
                throw e;
            } finally {
                recordData.setContent(recordData.getContentSB().toString());
                service.shutdownExecutor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause() instanceof ConnectException) {
                throw new BusinessException("服务器节点异常，请稍后重试或联系管理员");
            }
            throw new BusinessException(e.getMessage());
        } finally {
            messageSendHandler.close();
        }
        chatResponseSyncDataService.syncChatResponse(chatSdkAccount, response, recordList);

        return response;
    }

    /**
     * 转换为api请求参数
     *
     * @param recordList
     * @param account
     * @param requestConsumer
     * @return
     */
    @Override
    public ChatCompletionRequest convertRequest(List<RecordData> recordList, ChatSdkAccount account, Consumer<ChatCompletionRequest> requestConsumer) {
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(account.getModelValue());
        request.setN(1);
        request.setMaxTokens(account.getOnceToken());
        request.setLogitBias(new HashMap<>());

        List<ChatMessage> chatMessageList = recordList.stream().map(item -> {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setRole(item.getRole());
            chatMessage.setContent(item.getContent());
            return chatMessage;
        }).toList();
        request.setMessages(chatMessageList);

        OR.run(account.getExtendParam(), CollUtil::isNotEmpty, extendParam -> {
            OR.run(extendParam.get("temperature"), Objects::nonNull, e -> {
                request.setTemperature(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("topP"), Objects::nonNull, e -> {
                request.setTopP(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("presencePenalty"), Objects::nonNull, e -> {
                request.setPresencePenalty(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("frequencyPenalty"), Objects::nonNull, e -> {
                request.setFrequencyPenalty(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("user"), Objects::nonNull, e -> {
                request.setUser(e.toString());
            });
        });
        requestConsumer.accept(request);
        return request;
    }

}
