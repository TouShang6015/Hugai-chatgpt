package com.hugai.modules.session.entity.model;

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

    @ApiModelProperty(value = "用户会话号")
    private Integer sessionNum;

    @ApiModelProperty(value = "展示图")
    private String showImg;

}
