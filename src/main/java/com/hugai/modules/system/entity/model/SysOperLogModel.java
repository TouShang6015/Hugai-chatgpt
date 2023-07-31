package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 操作日志记录 Model
 *
 * @author WuHao
 * @date 2022-06-22 22:35:41
 * @tableName sys_oper_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_oper_log")
public class SysOperLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 模块标题
     */
    @ApiModelProperty(value = "模块标题", dataType = "String")
    private String title;
    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", dataType = "String")
    private String method;
    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", dataType = "String")
    private String requestMethod;
    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）", dataType = "Integer")
    private Integer operatorType;
    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员", dataType = "String")
    private String operName;
    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL", dataType = "String")
    private String operUrl;
    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址", dataType = "String")
    private String operIp;
    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点", dataType = "String")
    private String operLocation;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", dataType = "String")
    private String operParam;
    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数", dataType = "String")
    private String jsonResult;
    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）", dataType = "Integer")
    private Integer status;
    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息", dataType = "String")
    private String errorMsg;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间", dataType = "String")
    private String operTime;

}
