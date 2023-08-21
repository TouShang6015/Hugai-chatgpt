package com.hugai.modules.statistics.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.statistics.entity.vo.DeskStatisticsDataVO;
import com.hugai.modules.statistics.entity.vo.UserSessionStatisticsDataVO;
import com.hugai.modules.statistics.service.StatisticsService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.hugai.common.constants.RedisCacheKey.WebClientRequestCount;

/**
 * @author WuHao
 * @since 2023/8/10 15:37
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.STATISTICS)
public class StatisticsController {

    private final StatisticsService service;

    private final RedisUtil redisUtil;

    @GetMapping("/getUserDeskInfo")
    public Result getUserDeskInfo() {
        DeskStatisticsDataVO deskCommonData = service.getDeskCommonData();
        UserSessionStatisticsDataVO userSessionStatisticsData = new UserSessionStatisticsDataVO();
        Object webClientRequestCount = redisUtil.getCacheObject(WebClientRequestCount);
        try {
            userSessionStatisticsData = service.getUserSessionStatisticsData();
        } catch (Exception ignored) {}
        return Result.success()
                .put("deskCommonData",deskCommonData)
                .put("userSessionStatisticsData",userSessionStatisticsData)
                .put("webClientRequestCount", Optional.ofNullable(webClientRequestCount).orElse(0))
                ;
    }

}
