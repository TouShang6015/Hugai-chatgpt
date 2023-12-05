package com.hugai.core.chat.entity;

import com.hugai.common.modules.entity.session.valid.ChatSend;
import com.hugai.common.modules.entity.session.valid.SendDomain;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 对话请求参数
 *
 * @author WuHao
 * @since 2023/5/30 17:02
 */
@Data
public class ChatRequestParam {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 会话id
     */
    @NotNull(message = "sessionId不能为空", groups = {ChatSend.class, SendDomain.class})
    private Long sessionId;
    /**
     * 连接ID
     */
    @NotNull(message = "connectId不能为空", groups = {ChatSend.class, SendDomain.class})
    private String connectId;
    /**
     * 内容
     */
    @NotEmpty(message = "内容不能为空", groups = {ChatSend.class, SendDomain.class})
    private String content;
    /**
     * 会话类型 {@link com.hugai.common.enums.SessionType}
     */
    @NotEmpty(message = "会话类型不能为空", groups = {ChatSend.class, SendDomain.class})
    private String sessionType;
    /**
     * 领域会话唯一标识
     */
    @NotEmpty(message = "领域会话类型不能为空", groups = {SendDomain.class})
    private String domainUniqueKey;
    /**
     * 所使用的模型ID
     */
    @NotNull(message = "模型不能为空", groups = {ChatSend.class, SendDomain.class})
    private Long chatModelId;
    /**
     * 是否连续对话
     */
    private Boolean ifConc;

    /**
     * 扩展参数
     */
    private Map<String, Object> extendParam;

}
