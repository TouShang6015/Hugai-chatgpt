package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.org.bebas.core.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hugai.common.constants.RedisCacheKey.WebClientRequestCount;

/**
 * @author WuHao
 * @since 2023/8/18 11:48
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Common.COMMON + "/interface")
@Api(value = "接口统计")
public class InterfaceStatistics {

    private final RedisUtil redisUtil;

    @ApiOperation(value = "统计访问次数")
    @GetMapping("/info")
    public void incr() {
        redisUtil.incr(WebClientRequestCount);
    }

}
