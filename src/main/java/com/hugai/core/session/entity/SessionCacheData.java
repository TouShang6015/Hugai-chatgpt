package com.hugai.core.session.entity;

import com.hugai.core.session.valid.Send;
import com.hugai.core.session.valid.SendDomain;
import com.hugai.core.session.valid.SendDrawOpenAi;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 会话上下文缓存
 *
 * @author WuHao
 * @since 2023/5/30 17:02
 */
@Data
public class SessionCacheData {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 会话id
     */
    @NotNull(message = "sessionId不能为空", groups = {Send.class, SendDomain.class})
    private Long sessionId;
    /**
     * 会话id
     */
    @NotNull(message = "connectId不能为空", groups = {Send.class, SendDomain.class})
    private String connectId;

    /**
     * 内容
     */
    @NotEmpty(message = "内容不能为空", groups = {Send.class, SendDomain.class})
    private String content;

    /**
     * 当前会话的token总数
     */
    private int token;

    /**
     * 会话上下文最大的token数
     */
    private int contextMaxToken;

    /**
     * 会话类型
     */
    @NotEmpty(message = "会话类型不能为空", groups = {Send.class, SendDomain.class, SendDrawOpenAi.class})
    private String sessionType;
    /**
     * 领域会话组
     */
    private String domainGroup;
    /**
     * 领域会话唯一标识
     */
    @NotEmpty(message = "领域会话类型不能为空", groups = {SendDomain.class})
    private String domainUniqueKey;
    /**
     * 是否连续对话
     */
    private String ifConc;

    /**
     * 使用模型
     */
    private String useModel;

}
