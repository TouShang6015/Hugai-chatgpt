package com.hugai.chatsdk.baidu.serviceimpl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.baidu.client.BaiduClientFactory;
import com.hugai.chatsdk.baidu.entity.request.ChatRequest;
import com.hugai.chatsdk.baidu.entity.request.Message;
import com.hugai.chatsdk.baidu.oauth.AccessTokenOauthBaidu;
import com.hugai.chatsdk.baidu.sseListener.BaiduSseListener;
import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.handler.MessageSendHandler;
import com.hugai.chatsdk.common.service.ChatBusinessService;
import com.hugai.chatsdk.common.service.ChatResponseSyncDataService;
import com.hugai.common.enums.ChatRole;
import com.hugai.common.enums.flow.ChatSdkType;
import com.org.bebas.core.function.OR;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSources;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2024/1/12 10:47
 */
@Slf4j
@Service
public class BaiduChatBusinessServiceImpl implements ChatBusinessService<ChatRequest> {

    @Resource
    private AccessTokenOauthBaidu accessTokenOauthBaidu;
    @Resource
    private ChatResponseSyncDataService chatResponseSyncDataService;

    @Override
    public ChatSdkType type() {
        return ChatSdkType.baidu;
    }

    @Override
    public ChatSdkStorageResponse chatCompletionStream(List<RecordData> recordList, ChatSdkAccount chatSdkAccount) {
        String connectId = chatSdkAccount.getConnectId();
        String apiToken = chatSdkAccount.getApiToken();

        log.info("[平台-{}]  流式请求参数：{}", chatSdkAccount.getSdkUniqueKey(), JSON.toJSONString(recordList));

        // 初始化消息推送
        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);

        String accessToken = accessTokenOauthBaidu.getAccessTokenString(apiToken);

        // 构建连接对象
        Tuple2<OkHttpClient, Request.Builder> clientTuples = BaiduClientFactory.createClient(chatSdkAccount, accessToken);
        OkHttpClient client = clientTuples.getT1();
        Request.Builder requestBuilder = clientTuples.getT2();
        ChatRequest chatRequest = this.convertRequest(recordList, chatSdkAccount, req -> req.setStream(true));
        requestBuilder.post(RequestBody.create(JSON.toJSONString(chatRequest), MediaType.get("application/json")));
        // 创建sse监听
        BaiduSseListener sseListener = new BaiduSseListener(messageSendHandler);
        EventSources.createFactory(client).newEventSource(requestBuilder.build(), sseListener);

        try {
            boolean await = sseListener.latch.await(120, TimeUnit.SECONDS);
            if (!await) {
                sseListener.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        sseListener.close();

        ChatSdkStorageResponse chatSdkStorageResponse = sseListener.getChatSdkStorageResponse();
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
        ChatRequest request = new ChatRequest();

        List<RecordData> finalRecordDataList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            RecordData recordData = recordList.get(i);
            if (finalRecordDataList.size() % 2 == 0 && ChatRole.assistant.name().equals(recordData.getRole())){
                finalRecordDataList.add(RecordData.builder().role(ChatRole.user.name()).content(" ").build());
            }
            if (finalRecordDataList.size() % 2 == 1 && ChatRole.user.name().equals(recordData.getRole())){
                finalRecordDataList.add(RecordData.builder().role(ChatRole.assistant.name()).content(" ").build());
            }
            finalRecordDataList.add(recordData);
        }

        List<Message> chatMessageList = finalRecordDataList.stream().map(item -> {
            Message message = new Message();
            message.setRole(item.getRole());
            message.setContent(item.getContent());
            return message;
        }).toList();
        request.setMessages(chatMessageList);

        OR.run(account.getExtendParam(), CollUtil::isNotEmpty, extendParam -> {
            OR.run(extendParam.get("temperature"), Objects::nonNull, e -> {
                request.setTemperature(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("top_p"), Objects::nonNull, e -> {
                request.setTop_p(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("penalty_score"), Objects::nonNull, e -> {
                request.setPenalty_score(Double.valueOf(e.toString()));
            });
            OR.run(extendParam.get("system"), Objects::nonNull, e -> {
                request.setSystem(e.toString());
            });
            OR.run(extendParam.get("user_id"), Objects::nonNull, e -> {
                request.setUser_id(e.toString());
            });
        });
        requestConsumer.accept(request);
        return request;
    }
}
