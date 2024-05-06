package com.hugai.core.chat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.common.entity.ChatSdkStorageResponse;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.service.ChatResponseSyncDataService;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.hugai.modules.config.service.IChatKeysService;
import com.hugai.modules.session.service.SessionRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WuHao
 * @since 2023/12/27 8:55
 */
@Service
public class ChatResponseSyncData implements ChatResponseSyncDataService {

    @Resource
    private IChatKeysService chatKeysService;
    @Resource
    private SessionRecordService sessionRecordService;

    @Transactional
    @Override
    public List<SessionRecordModel> syncChatResponse(ChatSdkAccount requestParam, ChatSdkStorageResponse response, List<RecordData> recordList) {
        // 帐号异常停用key
        if (response.getAccountError()) {
            String apiToken = response.getAccount().getApiToken();
            chatKeysService.removeByApiToken(apiToken);
            return null;
        }
        ChatRequestParam chatRequestParam = JSON.parseObject(requestParam.getChatRequestParamJson(), ChatRequestParam.class);
        if (CollUtil.isNotEmpty(response.getResponseRecordData())) {
            return sessionRecordService.responseInsertHandle(recordList, chatRequestParam, response);
        }
        return null;
    }

}
