package com.hugai.modules.statistics.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.statistics.entity.vo.DeskStatisticsDataVO;
import com.hugai.modules.statistics.service.StatisticsService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "获取用户端设置中相关信息")
    @GetMapping("/getSettingInfo")
    public Result getSettingInfo() {
        DeskStatisticsDataVO deskCommonData = service.getDeskCommonData();
        Object webClientRequestCount = redisUtil.getCacheObject(WebClientRequestCount);
        return Result.success()
                .put("info",deskCommonData)
                .put("requestCount", Optional.ofNullable(webClientRequestCount).orElse(0))
                ;
    }

}
