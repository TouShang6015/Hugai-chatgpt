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

import javax.validation.constraints.NotEmpty;

/**
 * apikeys Model
 *
 * @author WuHao
 * @date 2023-05-26
 * @tableName tb_openai_keys
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_openai_keys")
public class OpenaiKeysModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * openai key
     */
    @NotEmpty(message = "apiKey不能为空",groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "openai key", dataType = "String")
    private String apiKey;
    /**
     * key名称
     */
    @ApiModelProperty(value = "key名称", dataType = "String")
    private String name;
    /**
     * 总额度
     */
    @ApiModelProperty(value = "总额度", dataType = "Double")
    private Double totalAmount;
    /**
     * 使用额度
     */
    @ApiModelProperty(value = "使用额度", dataType = "Double")
    private Double totalUsage;
    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间", dataType = "Date")
    private String expiredTime;
    /**
     * 是否通用key
     */
    @ApiModelProperty(value = "是否通用key")
    private String ifCommon;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 可用状态
     */
    @ApiModelProperty(value = "可用状态")
    private String enableStatus;

}
