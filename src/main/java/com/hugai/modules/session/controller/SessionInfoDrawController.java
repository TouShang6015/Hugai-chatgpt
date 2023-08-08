package com.hugai.modules.session.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会话表 控制器
 *
 * @author WuHao
 * @date 2023-05-29
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SESSION + "/sessioninfodraw")
@Api(value = "SessionInfoDrawModel", tags = "绘图会话")
public class SessionInfoDrawController {

    private final SessionInfoDrawService service;

    @GetMapping("/userLastDrawSession")
    @ApiOperation(value = "获取用户最新的画图会话")
    public Result userLastDrawSession(String drawUniqueKey) {
        return Result.success(service.getLastSession(drawUniqueKey));
    }

}
