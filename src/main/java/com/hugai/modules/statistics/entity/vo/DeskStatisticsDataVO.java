package com.hugai.modules.statistics.entity.vo;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/8/10 15:24
 */
@Data
public class DeskStatisticsDataVO {

    /**
     * 游客数
     */
    private Integer touristCount;
    /**
     * 会员数
     */
    private Integer userCount;
    /**
     * 运行天数
     */
    private Integer runDay;

}
