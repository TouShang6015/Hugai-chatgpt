package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 字典数据表 Model
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 * @tableName base_dict_data
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("base_dict_data")
public class BaseDictDataModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序", dataType = "Integer")
    private Integer sort;
    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签", dataType = "String")
    private String dictLabel;
    /**
     * 字典键值
     */
    @ApiModelProperty(value = "字典键值", dataType = "String")
    private String dictValue;
    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型", dataType = "String")
    private String dictType;
    /**
     * 样式属性（其他样式扩展）
     */
    @ApiModelProperty(value = "样式属性（其他样式扩展）", dataType = "String")
    private String cssClass;
    /**
     * 表格回显样式
     */
    @ApiModelProperty(value = "表格回显样式", dataType = "String")
    private String listClass;
    /**
     * 是否默认（Y是 N否）
     */
    @ApiModelProperty(value = "是否默认（Y是 N否）", dataType = "String")
    private String isDefault;
    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）", dataType = "String")
    private String status;

}
