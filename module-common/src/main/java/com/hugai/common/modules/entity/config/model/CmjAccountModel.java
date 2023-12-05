package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hugai.common.modules.serializer.SensitiveStringSerializer;
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
 * mj账户配置 Model
 *
 * @author wuhao
 * @date 2023-09-25
 * @tableName tb_cmj_account
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_cmj_account")
public class CmjAccountModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 账户名
     */
    @NotEmpty(message = "账户名不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "账户名", dataType = "String")
    private String userName;
    /**
     * token
     */
    @NotEmpty(message = "token不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "token", dataType = "String")
    private String userToken;
    /**
     * ua
     */
    @NotEmpty(message = "userAgent不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "ua", dataType = "String")
    private String userAgent;
    /**
     * dataObject
     */
    @NotEmpty(message = "dataObject不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "dataObject", dataType = "String")
    private String dataObject;

    private String accountStatus;

}
