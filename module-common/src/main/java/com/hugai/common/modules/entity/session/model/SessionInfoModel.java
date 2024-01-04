package com.hugai.common.modules.entity.session.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 会话表 Model
 *
 * @author WuHao
 * @date 2023-05-29
 * @tableName tb_session_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_session_info")
public class SessionInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "会话名称")
    private String sessionName;

    @ApiModelProperty(value = "领域会话类型唯一标识")
    private String domainUniqueKey;

    @ApiModelProperty(value = "会话状态", dataType = "String")
    private String status;

    @ApiModelProperty(value = "总消耗token")
    private Integer allConsumerToken;

    @ApiModelProperty(value = "最近一次使用的模型ID")
    private Long chatModelId;

}
