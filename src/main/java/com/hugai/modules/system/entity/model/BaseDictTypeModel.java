package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 字典类型表 Model
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 * @tableName base_dict_type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("base_dict_type")
public class BaseDictTypeModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型", dataType = "String")
    private String dictType;
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", dataType = "String")
    private String dictName;
    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）", dataType = "String")
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

}
