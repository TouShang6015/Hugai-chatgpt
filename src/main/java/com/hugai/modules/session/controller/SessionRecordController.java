package com.hugai.modules.session.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.Constants;
import com.hugai.modules.session.entity.dto.SessionRecordDTO;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.model.build.QueryFastLambda;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会话详情 控制器
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SESSION + "/sessionrecord")
@Api(value = "SessionRecordModel", tags = "会话详情")
public class SessionRecordController {

    private final SessionRecordService service;

    @ApiOperation(value = "获取会话（可分页）")
    @GetMapping("/getPageRecordSession")
    public Result getPageRecordSession(SessionRecordDTO param) {
        Long sessionId = param.getSessionId();
        Assert.notNull(sessionId, () -> new BusinessException("会话号为空"));

        QueryFastLambda.build(param).sortCondition(SessionRecordDTO::getId, true);
        IPage<SessionRecordModel> page = service.listPageByParam(PageUtil.pageBean(param), param);
        return Result.success(page);
    }

    @ApiOperation(value = "获取会话（不分页）")
    @GetMapping("/getRecordSession")
    public Result getRecordSession(SessionRecordDTO param) {
        Long sessionId = param.getSessionId();
        Assert.notNull(sessionId, () -> new BusinessException("会话号为空"));

        List<SessionRecordModel> result = OptionalUtil.ofNullList(service.cacheGetListBySessionId(sessionId)).stream()
                .filter(item -> Constants.BOOLEAN.TRUE.equals(item.getIfShow()))
                .sorted(Comparator.comparing(SessionRecordModel::getId))
                .collect(Collectors.toList());
        return Result.success(result);
    }

}
