package com.hugai.common.modules.entity.session.vo;

import com.hugai.common.modules.entity.session.valid.AddChatSession;
import com.hugai.common.modules.entity.session.valid.AddDomainSession;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 会话模块通用请求实体
 *
 * @author WuHao
 * @since 2023/6/22 10:15
 */
@Data
public class SessionBaseRequest {

    /**
     * 会话id
     */
    private Long sessionId;
    /**
     * 会话类型 {@link com.hugai.common.enums.SessionType}
     */
    @NotEmpty(message = "会话类型参数不能为空[sessionType]", groups = {AddChatSession.class, AddDomainSession.class})
    private String sessionType;
    /**
     * 领域会话唯一标识
     */
    @NotEmpty(message = "领域会话唯一标识不能为空[domainUniqueKey]", groups = {AddDomainSession.class})
    private String domainUniqueKey;

    /**
     * 内容
     */
    private String content;
}
