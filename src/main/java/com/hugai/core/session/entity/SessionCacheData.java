package com.hugai.core.session.entity;

import com.hugai.core.security.context.SecurityContextUtil;
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
     * 会话缓存id（浏览器传递）
     */
    @NotEmpty(message = "sseId不能为空", groups = {Send.class, SendDomain.class})
    private String sseId;
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

    public SessionCacheData() {
        this.userId = SecurityContextUtil.getUserId();
    }
}
