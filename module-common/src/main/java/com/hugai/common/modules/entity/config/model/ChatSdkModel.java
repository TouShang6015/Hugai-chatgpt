package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 对话第三方平台管理 Model
 *
 * @author wuhao
 * @date 2023-11-27
 * @tableName tb_chat_sdk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_chat_sdk")
public class ChatSdkModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一键
	 */
	@ApiModelProperty(value = "唯一键", dataType = "String")
	private String uniqueKey;
	/**
	 * 平台名称
	 */
	@ApiModelProperty(value = "平台名称", dataType = "String")
	private String sdkName;
	/**
	 * 可用状态
	 */
	@ApiModelProperty(value = "可用状态", dataType = "String")
	private String enableStatus;
	/**
	 * key池获取规则
	 */
	@ApiModelProperty(value = "key池获取规则", dataType = "String")
	private String keysRules;
	/**
	 * 是否开启镜像地址负载均衡
	 */
	@ApiModelProperty(value = "是否开启镜像地址负载均衡")
	private String loadBalanceHost;

}
