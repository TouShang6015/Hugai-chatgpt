package com.hugai.modules.session.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.session.valid.AddChatSession;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.common.modules.entity.session.valid.AddDomainSession;
import com.hugai.framework.log.annotation.Log;
import com.hugai.common.modules.entity.session.convert.SessionInfoConvert;
import com.hugai.common.modules.entity.session.dto.SessionInfoDTO;
import com.hugai.common.modules.entity.session.model.SessionInfoModel;
import com.hugai.common.modules.entity.session.vo.SessionBaseRequest;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
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
    @PostMapping("/addSession")
    @ApiOperation(value = "新增会话")
    public Result addSession(@RequestBody SessionBaseRequest param) {
        ValidatorUtil.validateEntity(param, AddChatSession.class);
        SessionInfoModel sessionInfoModel = service.addSession(param.getSessionType(), null, null);
        return Result.success(sessionInfoModel);
    }

    @Log(title = "新增领域会话")
    @PostMapping("/addDomainSession")
    @ApiOperation(value = "新增领域会话")
    public Result addDomainSession(@RequestBody SessionBaseRequest param) {
        ValidatorUtil.validateEntity(param, AddDomainSession.class);
        SessionInfoModel sessionInfoModel = service.addSession(param.getSessionType(), param.getDomainUniqueKey(), param.getContent());
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

    @ApiOperation(value = "获取会话信息")
    @GetMapping("/getSessionInfo/{sessionId}")
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
            SessionInfoModel sessionInfoModel = service.addSession(sessionType, null, null);
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
            SessionInfoModel sessionInfoModel = service.addSession(sessionType, domainUniqueKey, content);
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
    public Result getUserSessionList(SessionInfoModel param) {
        SessionInfoModel queryParam = new SessionInfoModel();
        queryParam.setSize(param.getSize());
        queryParam.setPage(param.getPage());
        queryParam.setType(param.getType());
        queryParam.setDomainUniqueKey(param.getDomainUniqueKey());
        queryParam.setUserId(SecurityContextUtil.getUserId());
        QueryFastLambda.build(queryParam).sortCondition(SessionInfoModel::getCreateTime, false);
        IPage<SessionInfoModel> page = service.listPageByParam(PageUtil.pageBean(queryParam), queryParam);
        return Result.success(page);
    }

    @ApiOperation(value = "管理端-列表分页")
    @PostMapping("/baseQueryPageByParam")
    public Result baseQueryPageByParam(@RequestBody SessionInfoDTO param) {
        if (
                StrUtil.isNotEmpty(param.getEmail()) ||
                        StrUtil.isNotEmpty(param.getIfTourist()) ||
                        StrUtil.isNotEmpty(param.getUserIpAddress()) ||
                        StrUtil.isNotEmpty(param.getUserName())
        ) {
            List<UserInfoModel> userList = userInfoService.lambdaQuery()
                    .select(UserInfoModel::getId)
                    .eq(StrUtil.isNotEmpty(param.getEmail()), UserInfoModel::getEmail, param.getEmail())
                    .like(StrUtil.isNotEmpty(param.getUserName()), UserInfoModel::getUserName, param.getUserName())
                    .eq(StrUtil.isNotEmpty(param.getUserIpAddress()), UserInfoModel::getIpaddress, param.getUserIpAddress())
                    .eq(StrUtil.isNotEmpty(param.getIfTourist()), UserInfoModel::getIfTourist, param.getIfTourist())
                    .list();
            List<Long> userIds = OptionalUtil.ofNullList(userList).stream().map(UserInfoModel::getId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            QueryFastLambda.<SessionInfoModel>build(param).queryConditionIn(SessionInfoModel::getUserId, userIds);
        }

        IPage<SessionInfoModel> modelPage = service.listPageByParam(PageUtil.pageBean(param), param);
        IPage<SessionInfoDTO> page = PageUtil.convert(modelPage, SessionInfoConvert.INSTANCE::convertToDTO);
        OR.run(page.getRecords(), CollUtil::isNotEmpty, dtoPage -> {
            List<Long> userIds = dtoPage.stream().map(SessionInfoDTO::getUserId).filter(Objects::nonNull).distinct().collect(Collectors.toList());

            List<UserInfoModel> userInfoList = OptionalUtil.ofNullList(
                    userInfoService.lambdaQuery()
                            .select(UserInfoModel::getUserName, UserInfoModel::getIpaddress, UserInfoModel::getEmail, UserInfoModel::getIfTourist, UserInfoModel::getId)
                            .in(UserInfoModel::getId, userIds)
                            .list()
            );

            dtoPage.forEach(item -> {
                UserInfoModel userInfoModel = userInfoList.stream().filter(x -> x.getId().equals(item.getUserId())).findFirst().orElseGet(UserInfoModel::new);
                item.setEmail(userInfoModel.getEmail());
                item.setUserName(userInfoModel.getUserName());
                item.setUserIpAddress(userInfoModel.getIpaddress());
                item.setIfTourist(userInfoModel.getIfTourist());
            });

        });

        return Result.success(page);
    }

}
