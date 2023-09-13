package com.hugai.modules.session.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.session.valid.AddDomainSession;
import com.hugai.core.session.valid.AddDrawSession;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.session.entity.convert.SessionInfoConvert;
import com.hugai.modules.session.entity.dto.SessionInfoDTO;
import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.hugai.modules.session.entity.vo.SessionBaseRequest;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.model.build.QueryFastLambda;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 会话表 控制器
 *
 * @author WuHao
 * @date 2023-05-29
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SESSION + "/sessioninfo")
@Api(value = "SessionInfoModel", tags = "会话表")
public class SessionInfoController {

    private final SessionInfoService service;

    private final UserInfoService userInfoService;

    @Log(title = "新增会话")
    @GetMapping("/addSession/{sessionType}")
    @ApiOperation(value = "新增会话")
    public Result addSession(@PathVariable String sessionType) {
        SessionInfoModel sessionInfoModel = service.addSession(sessionType);
        return Result.success(sessionInfoModel);
    }

    @Log(title = "新增领域会话")
    @PostMapping("/addDomainSession")
    @ApiOperation(value = "新增领域会话")
    public Result addDomainSession(@RequestBody SessionBaseRequest param) {
        ValidatorUtil.validateEntity(param, AddDomainSession.class);
        SessionInfoModel sessionInfoModel = service.addDomainSession(param.getSessionType(), param.getDomainUniqueKey(), param.getContent());
        return Result.success(sessionInfoModel);
    }

    @Log(title = "新增画图会话")
    @PostMapping("/addDrawSession")
    @ApiOperation(value = "新增画图会话")
    public Result addDrawSession(@RequestBody SessionBaseRequest param) {
        ValidatorUtil.validateEntity(param, AddDrawSession.class);
        SessionInfoModel sessionInfoModel = service.addDrawSession(param.getSessionType(), param.getDrawType());
        return Result.success(sessionInfoModel);
    }

    @Log(title = "删除会话")
    @DeleteMapping("/deleteSession/{sessionIds}")
    @ApiOperation(value = "删除会话")
    public Result deleteSession(@PathVariable String sessionIds) {
        List<String> list = StringUtils.splitToList(sessionIds, String::valueOf);
        service.removeByIds(list);
        return Result.success();
    }

    @GetMapping("/getSessionInfo/{sessionId}")
    @ApiOperation(value = "获取会话信息")
    public Result getSessionInfo(@PathVariable Long sessionId) {
        SessionInfoModel sessionInfo = service.getById(sessionId);
        return Result.success(sessionInfo);
    }

    @GetMapping("/userLastSession/{sessionType}")
    @ApiOperation(value = "获取用户最新的会话，根据sessionType")
    public Result userLastSession(@PathVariable String sessionType) {
        SessionInfoModel lastSession = service.userLastSession(sessionType);
        // 不存在就新建一条会话
        if (Objects.isNull(lastSession)) {
            SessionInfoModel sessionInfoModel = service.addSession(sessionType);
            return Result.success(sessionInfoModel);
        }
        return Result.success(lastSession);
    }

    @GetMapping("/userLastDomainSession")
    @ApiOperation(value = "获取用户最新的领域会话")
    public Result userLastDomainSession(String sessionType, String domainUniqueKey, String content) {
        SessionInfoModel lastSession = service.userLastDomainSession(sessionType, domainUniqueKey);
        // 不存在就新建一条会话
        if (Objects.isNull(lastSession)) {
            SessionInfoModel sessionInfoModel = service.addDomainSession(sessionType, domainUniqueKey, content);
            return Result.success(sessionInfoModel);
        }
        return Result.success(lastSession);
    }

    @GetMapping("/userLastDrawSession")
    @ApiOperation(value = "获取用户最新的画图会话")
    public Result userLastDrawSession(String sessionType, String drawUniqueKey) {
        SessionInfoModel lastSession = service.userLastDrawSession(sessionType, drawUniqueKey);
        // 不存在就新建一条会话
        if (Objects.isNull(lastSession)) {
            SessionInfoModel sessionInfoModel = service.addDrawSession(sessionType, drawUniqueKey);
            return Result.success(sessionInfoModel);
        }
        return Result.success(lastSession);
    }

    @Log(title = "清空会话列表")
    @GetMapping("/clearSession/{sessionId}")
    @ApiOperation(value = "清空会话列表")
    public Result clearSession(@PathVariable Long sessionId) {
        service.clearSession(sessionId);
        return Result.success();
    }

    @GetMapping("/getUserSessionList")
    @ApiOperation(value = "获取用户会话列表（分页）")
    public Result getUserSessionList(SessionInfoModel param){
        SessionInfoModel queryParam = new SessionInfoModel();
        queryParam.setSize(param.getSize());
        queryParam.setPage(param.getPage());
        queryParam.setType(param.getType());
        queryParam.setDomainUniqueKey(param.getDomainUniqueKey());
        queryParam.setDrawUniqueKey(param.getDrawUniqueKey());
        queryParam.setUserId(SecurityContextUtil.getUserId());
        QueryFastLambda.build(queryParam).sortCondition(SessionInfoModel::getCreateTime,false);
        IPage<SessionInfoModel> page = service.listPageByParam(PageUtil.pageBean(queryParam), queryParam);
        return Result.success(page);
    }

    @ApiOperation(value = "管理端-列表分页")
    @PostMapping("/baseQueryPageByParam")
    public Result baseQueryPageByParam(@RequestBody SessionInfoDTO param){
        IPage<SessionInfoModel> modelPage = service.listPageByParam(PageUtil.pageBean(param), param);
        IPage<SessionInfoDTO> page = PageUtil.convert(modelPage, SessionInfoConvert.INSTANCE::convertToDTO);
        OR.run(page.getRecords(), CollUtil::isNotEmpty,dtoPage -> {
            List<Long> userIds = dtoPage.stream().map(SessionInfoDTO::getUserId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            List<UserInfoModel> userInfoList = OptionalUtil.ofNullList(
                    userInfoService.lambdaQuery()
                            .select(UserInfoModel::getUserName, UserInfoModel::getIpaddress, UserInfoModel::getEmail, UserInfoModel::getId)
                            .in(UserInfoModel::getId, userIds)
                            .list()
            );

            dtoPage.forEach(item -> {
                UserInfoModel userInfoModel = userInfoList.stream().filter(x -> x.getId().equals(item.getUserId())).findFirst().orElseGet(UserInfoModel::new);
                item.setEmail(userInfoModel.getEmail());
                item.setUserName(userInfoModel.getUserName());
                item.setUserIpAddress(userInfoModel.getIpaddress());
            });

        });

        return Result.success(page);
    }

}
