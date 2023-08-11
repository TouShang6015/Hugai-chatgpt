package com.hugai.modules.statistics.entity.vo;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/8/10 15:26
 */
@Data
public class UserSessionStatisticsDataVO {

    /**
     * 显示名称
     */
    private String showName;
    /**
     * 会话数
     */
    private Integer sessionCount;
    /**
     * 绘图会话数
     */
    private Integer sessionDrawCount;
    /**
     * token 消耗
     */
    private Integer tokenConsumer;
    /**
     * 加入时间
     */
    private String joinTime;
    /**
     * token key数量
     */
    private Integer tokenNum;

}
