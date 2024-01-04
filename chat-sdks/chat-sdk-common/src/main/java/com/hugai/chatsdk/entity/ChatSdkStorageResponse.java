package com.hugai.chatsdk.entity;

import cn.hutool.core.collection.CollUtil;
import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.session.RecordData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话待持久化存储集合
 *
 * @author WuHao
 * @since 2023/11/28 9:34
 */
@Data
public class ChatSdkStorageResponse {

    /**
     * 账号
     */
    private ChatSdkAccount account;

    /**
     * 响应正文
     */
    private List<RecordData> responseRecordData;

    private Long customerToken;


    /**
     * 账号是否可用
     */
    private Boolean accountError = false;

    public RecordData pushData(RecordData data) {
        if (CollUtil.isEmpty(this.responseRecordData)) {
            this.responseRecordData = new ArrayList<>();
        }
        this.responseRecordData.add(data);
        return data;
    }

}
