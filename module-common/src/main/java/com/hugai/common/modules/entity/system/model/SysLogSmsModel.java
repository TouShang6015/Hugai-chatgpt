package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 验证码日志 Model
 *
 * @author wuhao
 * @date 2023-12-07
 * @tableName sys_log_sms
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log_sms")
public class SysLogSmsModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 短信类型
     */
    @ApiModelProperty(value = "短信类型", dataType = "String")
    private String smsType;
    /**
     * 短信类型描述
     */
    @ApiModelProperty(value = "短信类型描述", dataType = "String")
    private String smsTypeDescription;
    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uniqueKey;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;

}
