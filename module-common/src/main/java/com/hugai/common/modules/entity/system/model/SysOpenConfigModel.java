package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hugai.common.modules.serializer.SensitiveStringSerializer;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 第三方配置 Model
 *
 * @author wuhao
 * @date 2023-12-20
 * @tableName sys_open_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_open_config")
public class SysOpenConfigModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 策略标识
     */
    @ApiModelProperty(value = "策略标识", dataType = "String")
    private String uniqueKey;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", dataType = "String")
    private String title;
    /**
     * appId
     */
    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "appId", dataType = "String")
    private String appId;
    /**
     * accessKey
     */
    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "accessKey", dataType = "String")
    private String accessKey;
    /**
     * secretKey
     */
    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "secretKey ", dataType = "String")
    private String secretKey;
    /**
     * 存储空间名称
     */
    @ApiModelProperty(value = "存储空间名称", dataType = "String")
    private String bucketName;
    /**
     * 数据处理服务域名
     */
    @ApiModelProperty(value = "数据处理服务域名", dataType = "String")
    private String dataHandleDomain;
    /**
     * 表单数据
     */
    @ApiModelProperty(value = "表单数据")
    private String formData;
    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    private String callbackUrl;
    /**
     * 商户ID
     */
    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "商户ID")
    private String tenantId;

    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "网关")
    private String getawayUrl;

    @ApiModelProperty(value = "同步跳转地址")
    private String returnUrl;

    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "应用公钥")
    private String softwarePublicKey;
}
