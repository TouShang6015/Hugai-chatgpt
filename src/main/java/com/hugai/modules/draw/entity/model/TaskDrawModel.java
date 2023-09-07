package com.hugai.modules.draw.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 绘图任务
 *
 * @author WuHao
 * @since 2023-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_task_draw")
public class TaskDrawModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "绘图任务类型")
    private String drawType;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "任务状态")
    private String taskStatus;

    @ApiModelProperty(value = "绘图会话id")
    private String sessionInfoDrawId;

    @ApiModelProperty(value = "任务结束时间")
    private String taskEndTime;

}
