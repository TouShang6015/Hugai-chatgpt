package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 系统参数配置表 Model
 *
 * @author WuHao
 * @date 2022-05-21 09:02:14
 * @tableName base_resource_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("base_resource_config")
public class BaseResourceConfigModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 参数键
     */
    @ApiModelProperty(value = "参数键", dataType = "String")
    private String configKey;
    /**
     * 资源值
     */
    @ApiModelProperty(value = "资源值", dataType = "String")
    private String resourceValue;

}
