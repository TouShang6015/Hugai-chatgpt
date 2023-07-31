package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
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

    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    @ApiModelProperty(value = "secretKey ")
    private String secretKey;

    @ApiModelProperty(value = "存储空间名称")
    private String bucketName;

    @ApiModelProperty(value = "数据处理服务域名")
    private String dataHandleDomain;

}
