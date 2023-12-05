package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hugai.common.modules.serializer.SensitiveStringSerializer;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 对话秘钥池 Model
 *
 * @author wuhao
 * @date 2023-11-27
 * @tableName tb_chat_keys
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_chat_keys")
public class ChatKeysModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联ID
	 */
	@ApiModelProperty(value = "关联ID", dataType = "Long")
	private Long chatSdkId;
	/**
	 * app_id
	 */
	@JsonSerialize(using = SensitiveStringSerializer.class)
	@ApiModelProperty(value = "app_id", dataType = "String")
	private String appId;
	/**
	 * apiToken
	 */
	@JsonSerialize(using = SensitiveStringSerializer.class)
	@ApiModelProperty(value = "apiToken", dataType = "String")
	private String apiToken;
	/**
	 * 别名
	 */
	@ApiModelProperty(value = "别名", dataType = "String")
	private String aliasName;
	/**
	 * 过期时间
	 */
	@ApiModelProperty(value = "过期时间", dataType = "String")
	private String expiredTime;
	/**
	 * 可用状态
	 */
	@ApiModelProperty(value = "可用状态", dataType = "String")
	private String enableStatus;
	/**
	 * 权重
	 */
	@ApiModelProperty(value = "权重", dataType = "Integer")
	private Integer weightValue;

	private String remark;

}
