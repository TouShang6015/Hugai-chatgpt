package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hugai.common.modules.serializer.SensitiveStringSerializer;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author WuHao
 * @since 2023/7/19 17:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_minio_secret")
public class SysMinioSecretModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "策略标识")
    private String uniqueKey;

    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "secretKey ")
    private String secretKey;

    @ApiModelProperty(value = "存储空间名称")
    private String bucketName;

    @JsonSerialize(using = SensitiveStringSerializer.class)
    @ApiModelProperty(value = "数据处理服务域名")
    private String dataHandleDomain;

}
