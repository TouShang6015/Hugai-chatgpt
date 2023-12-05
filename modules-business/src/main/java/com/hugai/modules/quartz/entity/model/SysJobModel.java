package com.hugai.modules.quartz.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 定时任务调度表 Model
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 * @tableName sys_job
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_job")
public class SysJobModel extends BaseModel implements Serializable {

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
     * cron执行表达式
     */
    @ApiModelProperty(value = "cron执行表达式", dataType = "String")
    private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @ApiModelProperty(value = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）", dataType = "String")
    private String misfirePolicy;
    /**
     * 是否并发执行（0允许 1禁止）
     */
    @ApiModelProperty(value = "是否并发执行（0允许 1禁止）", dataType = "String")
    private String concurrent;
    /**
     * 状态（0正常 1暂停）
     */
    @ApiModelProperty(value = "状态（0正常 1暂停）", dataType = "String")
    private String status;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", dataType = "String")
    private String remark;

    @ApiModelProperty(value = "是否持久化")
    private String ifSaveLog;

}
