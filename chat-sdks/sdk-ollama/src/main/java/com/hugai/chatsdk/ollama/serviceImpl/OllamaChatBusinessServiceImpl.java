package com.hugai.chatsdk.ollama.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.handler.MessageSendHandler;
import com.hugai.chatsdk.common.service.ChatBusinessService;
import com.hugai.chatsdk.common.service.ChatResponseSyncDataService;
import com.hugai.chatsdk.ollama.api.ApiService;
import com.hugai.chatsdk.ollama.client.OllamaClientFactory;
import com.hugai.chatsdk.ollama.entity.ChatCompletionsRequest;
import com.hugai.chatsdk.ollama.entity.ChatCompletionsResponse;
import com.hugai.chatsdk.ollama.entity.Message;
import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author wuhao
 * @since 2024/5/26 3:33
 */
@Slf4j
@Service
public class OllamaChatBusinessServiceImpl implements ChatBusinessService<ChatCompletionsRequest> {

    @Resource
    private ChatResponseSyncDataService chatResponseSyncDataService;

    @Override
    public ChatSdkType type() {
        return ChatSdkType.ollama;
    }

    @Override
    public ChatSdkStorageResponse chatCompletionStream(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {
        String connectId = chatSdkAccount.getConnectId();

        log.info("[平台-{}]  流式请求参数：{}", chatSdkAccount.getSdkUniqueKey(), JSON.toJSONString(recordList));

        // 初始化消息推送
        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);
        final CountDownLatch latch = new CountDownLatch(1);

        ApiService service = OllamaClientFactory.getService(chatSdkAccount);

        ChatSdkStorageResponse response = new ChatSdkStorageResponse();
        response.setAccount(chatSdkAccount);
        ChatCompletionsRequest chatRequest = this.convertRequest(recordList, chatSdkAccount, req -> req.setStream(true));
        RecordData recordData = response.pushData(new RecordData());
        try {
            service.streamChatCompletion(chatRequest, new Callback<>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try (
                            ResponseBody body = response.body();
                            InputStream inputStream = Objects.requireNonNull(body).byteStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
                    ) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            ChatCompletionsResponse res = JSON.parseObject(line, ChatCompletionsResponse.class);
                            Message message = res.getMessage();
                            OR.run(message.getRole(), StrUtil::isNotEmpty, recordData::setRole);
                            String resContent = Optional.ofNullable(message.getContent()).orElse("");
                            recordData.getContentSB().append(resContent);
                            // 发送消息
                            messageSendHandler.queueAdd(resContent);
                        }
                    } catch (IOException e) {
                        throw new BusinessException("IO error");
                    } finally {
                        latch.countDown();
                        messageSendHandler.close();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    log.debug("Streaming completion error", throwable);
                    latch.countDown();
                }
            });
        } catch (BusinessException e) {
            if (e.getCode() == HttpStatus.HTTP_OK) {
                return response;
            }
            throw e;
        }

        try {
            boolean await = latch.await(120, TimeUnit.SECONDS);
            if (!await) {
                service.shutdownExecutor();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        recordData.setContent(recordData.getContentSB().toString());
        service.shutdownExecutor();


        List<SessionRecordModel> sessionRecordModels = chatResponseSyncDataService.syncChatResponse(chatSdkAccount, response, recordList);

        return response;
    }

    @Override
    public ChatSdkStorageResponse chatCompletion(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {
        String connectId = chatSdkAccount.getConnectId();

        log.info("[平台-{}]  非流式请求参数：{}", chatSdkAccount.getSdkUniqueKey(), JSON.toJSONString(recordList));

        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);

        ChatSdkStorageResponse response = new ChatSdkStorageResponse();

        try {
            ApiService service = OllamaClientFactory.getService(chatSdkAccount);
            ChatCompletionsRequest request = this.convertRequest(recordList, chatSdkAccount, e -> e.setStream(false));

            RecordData recordData = response.pushData(new RecordData());
            try {
                request.setStream(false);
                ChatCompletionsResponse res = service.createChatCompletion(request);

                OR.run(res.getMessage(),Objects::nonNull,message -> {
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

    @Override
    public ChatCompletionsRequest convertRequest(List<RecordData> recordList, ChatSdkAccount account, Consumer<ChatCompletionsRequest> requestConsumer) {
        ChatCompletionsRequest request = new ChatCompletionsRequest();

        List<Message> messageList = recordList.stream().map(item -> {
            Message chatMessage = new Message();
            chatMessage.setRole(item.getRole());
            chatMessage.setContent(item.getContent());
            return chatMessage;
        }).toList();
        request.setMessages(messageList);
        request.setModel(account.getModelValue());

        OR.run(account.getExtendParam(), CollUtil::isNotEmpty, extendParam -> {
            OR.run(extendParam.get("format"), Objects::nonNull, e -> {
                request.setFormat(String.valueOf(e));
            });
            OR.run(extendParam.get("options"), Objects::nonNull, e -> {
                request.setOptions(JSON.parseObject(JSON.toJSONString(e), Map.class));
            });
            OR.run(extendParam.get("stream"), Objects::nonNull, e -> {
                request.setStream((Boolean) e);
            });
        });
        requestConsumer.accept(request);
        return request;
    }
}
