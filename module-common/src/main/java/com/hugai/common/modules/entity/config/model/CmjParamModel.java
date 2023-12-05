package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import com.org.bebas.core.validator.group.GroupInsert;
import com.org.bebas.core.validator.group.GroupUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * mj参数配置 Model
 *
 * @author wuhao
 * @date 2023-09-25
 * @tableName tb_cmj_param
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_cmj_param")
public class CmjParamModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @NotNull(message = "唯一标识", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uniqueKey;

    /**
     * 参数值
     */
    @NotNull(message = "参数值", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "参数值", dataType = "String")
    private String paramValue;

}
