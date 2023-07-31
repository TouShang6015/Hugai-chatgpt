package com.hugai.modules.session.entity.vo;

import com.hugai.core.session.valid.AddDomainSession;
import com.hugai.core.session.valid.AddDrawSession;
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
     * 会话类型 {@link com.hugai.common.enums.flow.SessionType}
     */
    @NotEmpty(message = "会话类型参数不能为空[sessionType]",groups = {AddDomainSession.class, AddDrawSession.class})
    private String sessionType;
    /**
     * 会话类型 {@link com.hugai.common.enums.flow.SessionType}
     */
    @NotEmpty(message = "绘画类型参数不能为空[drawType;]",groups = {AddDrawSession.class})
    private String drawType;
    /**
     * 领域会话唯一标识
     */
    @NotEmpty(message = "领域会话唯一标识不能为空[domainUniqueKey]",groups = {AddDomainSession.class})
    private String domainUniqueKey;

    /**
     * 内容
     */
    private String content;
}
