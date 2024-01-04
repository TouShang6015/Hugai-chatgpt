package com.hugai.chatsdk.spark.serviceimpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.account.ClientParam;
import com.hugai.chatsdk.entity.session.RecordData;
import com.hugai.chatsdk.handler.MessageSendHandler;
import com.hugai.chatsdk.service.ChatBusinessService;
import com.hugai.chatsdk.service.ChatResponseSyncDataService;
import com.hugai.chatsdk.spark.client.SparkClientFactory;
import com.hugai.chatsdk.spark.entity.Text;
import com.hugai.chatsdk.spark.entity.request.*;
import com.hugai.chatsdk.spark.socket.SparkChatSocketListener;
import com.hugai.common.enums.flow.ChatSdkType;
import com.org.bebas.core.function.OR;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author WuHao
 * @since 2023/12/27 11:17
 */
@Slf4j
@Service
public class SparkChatBusinessServiceImpl implements ChatBusinessService<ChatRequest> {

    @Resource
    private ChatResponseSyncDataService chatResponseSyncDataService;

    @Override
    public ChatSdkType type() {
        return ChatSdkType.spark;
    }

    @Override
    public ChatSdkStorageResponse chatCompletionStream(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {
        String connectId = chatSdkAccount.getConnectId();

        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);
        ChatRequest chatRequest = this.convertRequest(recordList, chatSdkAccount, null);

        Tuple2<OkHttpClient, Request> clientTuples = SparkClientFactory.createClient(chatSdkAccount);
        OkHttpClient client = clientTuples.getT1();
        Request request = clientTuples.getT2();

        log.info("[平台-{}]  流式请求参数：{}", chatSdkAccount.getSdkUniqueKey(), JSON.toJSONString(chatRequest));

        SparkChatSocketListener socketListener = new SparkChatSocketListener(chatRequest, messageSendHandler, new ChatSdkStorageResponse());
        client.newWebSocket(request, socketListener);

        try {
            boolean await = socketListener.latch.await(120, TimeUnit.SECONDS);
            if (!await){
                socketListener.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        socketListener.close();

        ChatSdkStorageResponse chatSdkStorageResponse = socketListener.getChatSdkStorageResponse();
        chatSdkStorageResponse.setAccount(chatSdkAccount);
        chatResponseSyncDataService.syncChatResponse(chatSdkAccount, chatSdkStorageResponse, recordList);
        return chatSdkStorageResponse;
    }

    @Override
    public ChatSdkStorageResponse chatCompletion(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {
        return null;
    }

    @Override
    public ChatRequest convertRequest(List<RecordData> recordList, ChatSdkAccount account, Consumer<ChatRequest> requestConsumer) {
        ClientParam clientParam = account.getClientParam();
        String baseUrl = clientParam.getBaseUrl();

        Function<String, String> getDomain = url -> {
            if (StrUtil.contains(url, "v1.1")) {
                return "general";
            } else if (StrUtil.contains(url, "v2.1")) {
                return "generalv2";
            } else if (StrUtil.contains(url, "v3.1")) {
                return "generalv3";
            }
            return null;
        };

        final ChatRequest chatRequest = new ChatRequest();

        final Header header = new Header();
        header.setUid(String.valueOf(account.getUserId()));
        header.setApp_id(account.getAppId());

        final Parameter parameter = new Parameter();
        Chat chat = new Chat();
        chat.setDomain(getDomain.apply(baseUrl));
        chat.setMax_tokens(account.getOnceToken());
        parameter.setChat(chat);

        Payload payload = new Payload();
        Payload.Message message = new Payload.Message();
        message.setText(
                recordList.stream().map(item -> {
                    Text chatMessage = new Text();
                    chatMessage.setRole(item.getRole());
                    chatMessage.setContent(item.getContent());
                    return chatMessage;
                }).toList()
        );
        payload.setMessage(message);

        OR.run(account.getExtendParam(), CollUtil::isNotEmpty, extendParam -> {
            OR.run(extendParam.get("temperature"), Objects::nonNull, e -> {
                chat.setTemperature(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("top_k"), Objects::nonNull, e -> {
                chat.setTop_k((Integer) e);
            });
        });

        chatRequest.setHeader(header);
        chatRequest.setParameter(parameter);
        chatRequest.setPayload(payload);
        return chatRequest;
    }
}
