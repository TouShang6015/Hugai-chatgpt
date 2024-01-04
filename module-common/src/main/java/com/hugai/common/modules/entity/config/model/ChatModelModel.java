package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 对话模型管理 Model
 *
 * @author wuhao
 * @date 2023-11-27
 * @tableName tb_chat_model
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_chat_model")
public class ChatModelModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联ID
	 */
	@ApiModelProperty(value = "关联ID", dataType = "Long")
	private Long chatSdkId;
	/**
	 * 唯一标识
	 */
	@ApiModelProperty(value = "唯一标识", dataType = "String")
	private String uniqueKey;
	/**
	 * 模型描述
	 */
	@ApiModelProperty(value = "模型描述", dataType = "String")
	private String modelDescription;
	/**
	 * 单次对话token限制
	 */
	@ApiModelProperty(value = "单次对话token限制")
	private Integer onceToken;
	/**
	 * 最大请求token限制
	 */
	@ApiModelProperty(value = "最大请求token限制")
	private Integer maxToken;
	/**
	 * model参数值
	 */
	@ApiModelProperty(value = "model参数值")
	private String modelValue;

	/**
	 * 排序号
	 */
	private Integer sortNo;
	/**
	 * 是否增强模型
	 */
	private String ifPlusModel;
	/**
	 * 请求地址
	 */
	private String requestUrl;
	/**
	 * 可用状态
	 */
	@ApiModelProperty(value = "可用状态", dataType = "String")
	private String enableStatus;

}
