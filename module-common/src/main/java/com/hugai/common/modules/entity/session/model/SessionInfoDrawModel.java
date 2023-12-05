package com.hugai.common.modules.entity.session.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 绘图会话 Model
 *
 * @author WuHao
 * @date 2023-05-29
 * @tableName tb_session_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_session_info_draw")
public class SessionInfoDrawModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "prompt")
    private String prompt;

    @ApiModelProperty(value = "绘画接口类型唯一标识")
    private String drawUniqueKey;

    @ApiModelProperty(value = "绘图接口标识")
    private String drawApiKey;

    @ApiModelProperty(value = "展示图")
    private String showImg;

    @ApiModelProperty(value = "底图")
    private String baseImg;

    @ApiModelProperty(value = "sd info响应参数json")
    private String sdResponseInfo;

    @ApiModelProperty(value = "mj原图地址")
    private String originalImgUrl;

    @ApiModelProperty(value = "任务Id")
    private String taskId;

    @ApiModelProperty(value = "原始任务ID")
    private Long originalTaskDrawId;

}
