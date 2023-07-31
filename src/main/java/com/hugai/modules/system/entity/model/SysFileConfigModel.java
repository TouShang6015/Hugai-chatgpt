package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件上传配置 Model
 *
 * @author WuHao
 * @date 2023-05-29
 * @tableName sys_file_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_file_config")
public class SysFileConfigModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 策略标识
     */
    @ApiModelProperty(value = "策略标识", dataType = "String")
    private String uniqueKey;
    /**
     * 保存路径
     */
    @ApiModelProperty(value = "保存路径", dataType = "String")
    private String savePath;

}
