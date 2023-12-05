package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 对话镜像地址管理 Model
 *
 * @author wuhao
 * @date 2023-11-27
 * @tableName tb_chat_sdk_host
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_chat_sdk_host")
public class ChatSdkHostModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联ID
	 */
	@ApiModelProperty(value = "关联ID", dataType = "Long")
	private Long chatSdkId;
	/**
	 * 请求地址
	 */
	@ApiModelProperty(value = "请求地址", dataType = "String")
	private String hostUrl;
	/**
	 * 可用状态
	 */
	@ApiModelProperty(value = "可用状态", dataType = "String")
	private String enableStatus;
	/**
	 * 是否默认
	 */
	@ApiModelProperty(value = "是否默认", dataType = "String")
	private String ifDefault;
	/**
	 * 是否开启代理
	 */
	@ApiModelProperty(value = "是否开启代理", dataType = "String")
	private String ifProxy;

	/**
	 * 请求超时时间（秒）
	 */
	@ApiModelProperty(value = "请求超时时间（秒）")
	private Integer timeoutValue;
	/**
	 * 最大连接数
	 */
	@ApiModelProperty(value = "最大连接数")
	private Integer maxConnect;
	/**
	 * 权重
	 */
	@ApiModelProperty(value = "权重")
	private Integer weightValue;

}
