package com.hugai.modules.quartz.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 定时任务调度日志表 Model
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 * @tableName sys_job_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_job_log")
public class SysJobLogModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", dataType = "String")
    private String jobName;
    /**
     * 任务组名
     */
    @ApiModelProperty(value = "任务组名", dataType = "String")
    private String jobGroup;
    /**
     * 调用目标字符串
     */
    @ApiModelProperty(value = "调用目标字符串", dataType = "String")
    private String invokeTarget;
    /**
     * 日志信息
     */
    @ApiModelProperty(value = "日志信息", dataType = "String")
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    @ApiModelProperty(value = "执行状态（0正常 1失败）", dataType = "String")
    private String status;
    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息", dataType = "String")
    private String exceptionInfo;

}
