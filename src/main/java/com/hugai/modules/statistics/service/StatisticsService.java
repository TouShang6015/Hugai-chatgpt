package com.hugai.modules.statistics.service;

import com.hugai.modules.statistics.entity.vo.DeskStatisticsDataVO;
import com.hugai.modules.statistics.entity.vo.UserSessionStatisticsDataVO;

/**
 * @author WuHao
 * @since 2023/8/10 15:36
 */
public interface StatisticsService {

    /**
     * 桌面部分信息返回
     *
     * @return
     */
    DeskStatisticsDataVO getDeskCommonData();

    /**
     * 获取用户会话统计信息
     *
     * @return
     */
    UserSessionStatisticsDataVO getUserSessionStatisticsData();

}
