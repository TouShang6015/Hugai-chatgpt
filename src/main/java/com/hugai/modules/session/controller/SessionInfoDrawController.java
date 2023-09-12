package com.hugai.modules.session.controller;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.vo.SessionDrawDetailVO;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

    private final SessionRecordDrawService sessionRecordDrawService;

    @GetMapping("/userLastDrawSession")
    @ApiOperation(value = "获取用户最新的画图会话")
    public Result userLastDrawSession(String drawUniqueKey) {
        return Result.success(service.getLastSession(drawUniqueKey));
    }

    @ApiOperation(value = "获取会话详情")
    @GetMapping("/getSessionDetail/{id}")
    public Result getSessionDetail(@PathVariable Long id){
        SessionInfoDrawModel sessionInfoDrawModel = service.cacheGetById(id);
        SessionDrawDetailVO detail = Optional.ofNullable(
                JSON.parseObject(JSON.toJSONString(sessionInfoDrawModel), SessionDrawDetailVO.class)
        ).orElseGet(SessionDrawDetailVO::new);

        detail.setRecordDrawList(sessionRecordDrawService.cacheGetBySessionInfoId(id));
        return Result.success(detail);
    }

}
