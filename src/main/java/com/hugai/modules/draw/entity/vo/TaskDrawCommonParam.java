package com.hugai.modules.draw.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 绘图任务公共参数实体
 *
 * @author WuHao
 * @since 2023/9/7 20:29
 */
@Data
public class TaskDrawCommonParam implements Serializable {

    /**
     * 绘画接口类型
     */
    private String drawType;

    /**
     * 绘画接口api标识
     */
    private String apiKey;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 请求参数
     */
    private String paramJsonString;

}
