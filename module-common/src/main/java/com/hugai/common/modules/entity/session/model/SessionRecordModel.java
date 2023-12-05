package com.hugai.common.modules.entity.session.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 会话详情 Model
 *
 * @author WuHao
 * @date 2023-05-29
 * @tableName tb_session_record
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_session_record")
public class SessionRecordModel extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;
    /**
     * 会话id
     */
    @ApiModelProperty(value = "会话id", dataType = "String")
    private Long sessionId;

    @ApiModelProperty(value = "领域类型唯一标识")
    private String domainUniqueKey;
    /**
     * 角色
     */
    @ApiModelProperty(value = "角色", dataType = "String")
    private String role;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;
    /**
     * 是否显示
     */
    @ApiModelProperty(value = "是否显示", dataType = "String")
    private String ifShow;
    /**
     * 是否可统计为上下文 0 否 1 是
     */
    @ApiModelProperty(value = "是否可统计为上下文 0 否 1 是", dataType = "String")
    private String ifContext;

    @ApiModelProperty(value = "是否为领域会话的最上文", dataType = "String")
    private String ifDomainTop;

    @ApiModelProperty(value = "消耗token数", dataType = "Integer")
    private Integer consumerToken;

    @ApiModelProperty(value = "使用模型ID")
    private Long chatModelId;

    @ApiModelProperty(value = "模型值")
    private String chatModelValue;

}
