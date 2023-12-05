package com.hugai.modules.session.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.common.modules.entity.session.vo.SessionDrawDetailVO;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.flowenum.utils.FlowEnumUtils;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会话表 控制器
 *
 * @author WuHao
 * @date 2023-05-29
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SESSION + "/sessioninfodraw")
@Api(value = "SessionnfoDrawModel", tags = "绘图会话")
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
    public Result getSessionDetail(@PathVariable Long id) {
        SessionInfoDrawModel sessionInfoDrawModel = service.cacheGetById(id);
        SessionDrawDetailVO detail = Optional.ofNullable(
                JSON.parseObject(JSON.toJSONString(sessionInfoDrawModel), SessionDrawDetailVO.class)
        ).orElseGet(SessionDrawDetailVO::new);

        List<SessionRecordDrawModel> sessionRecordDrawList = OptionalUtil.ofNullList(sessionRecordDrawService.cacheGetBySessionInfoId(id));

        DrawType drawTypeEnum = FlowEnumUtils.getEnumByKey(sessionInfoDrawModel.getDrawUniqueKey(), DrawType.class);
        if (drawTypeEnum.equals(DrawType.MJ)) {
            Long originalTaskDrawId = sessionInfoDrawModel.getOriginalTaskDrawId();
            List<String> originalTaskDrawIds = CollUtil.newArrayList(sessionInfoDrawModel.getTaskId(), String.valueOf(OptionalUtil.ofNullLong(originalTaskDrawId)));
            List<Long> recordIds = sessionRecordDrawList.stream().map(SessionRecordDrawModel::getId).distinct().collect(Collectors.toList());
            List<SessionRecordDrawModel> mjRecordModelList = sessionRecordDrawService.lambdaQuery()
                    .in(SessionRecordDrawModel::getOriginalTaskDrawId, originalTaskDrawIds)
                    .eq(SessionRecordDrawModel::getUserId, sessionInfoDrawModel.getUserId())
                    .list();
            sessionRecordDrawList.addAll(
                    OptionalUtil.ofNullList(mjRecordModelList).stream().filter(item -> !recordIds.contains(item.getId())).collect(Collectors.toList())
            );
        }
        detail.setRecordDrawList(sessionRecordDrawList.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        return Result.success(detail);
    }

}
