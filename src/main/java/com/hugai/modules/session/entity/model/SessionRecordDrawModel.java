package com.hugai.modules.session.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 图像会话详情 Model
 *
 * @author WuHao
 * @date 2023-05-29
 * @tableName tb_session_record_draw
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_session_record_draw")
public class SessionRecordDrawModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "绘图会话id")
    private Long sessionInfoDrawId;

    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "绘画接口类型唯一标识")
    private String drawUniqueKey;

    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;

    @ApiModelProperty(value = "图片base64内容")
    private String drawBase64Img;

    @ApiModelProperty(value = "图片路径")
    private String drawImgUrl;

    @ApiModelProperty(value = "prompt")
    private String prompt;

}
