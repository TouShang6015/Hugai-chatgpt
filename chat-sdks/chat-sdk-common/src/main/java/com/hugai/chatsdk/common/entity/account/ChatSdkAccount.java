package com.hugai.chatsdk.common.entity.account;

import lombok.Data;

import java.util.Map;

/**
 * @author WuHao
 * @since 2023/11/29 10:34
 */
@Data
public class ChatSdkAccount {

    private Long userId;
    /**
     * 平台唯一标识
     */
    private String sdkUniqueKey;
    /**
     * 连接ID
     */
    private String connectId;
    /**
     * 模型名
     */
    private String modelValue;
    /**
     * token
     */
    private String apiToken;
    /**
     * appid
     */
    private String appId;
    /**
     * apiSecret
     */
    private String apiSecret;

    /**
     * 单次token值
     */
    private Integer onceToken;

    /**
     * 客户端参数
     */
    private ClientParam clientParam;

    /**
     * 扩展参数
     */
    private Map<String, Object> extendParam;

    private String chatRequestParamJson;

}
