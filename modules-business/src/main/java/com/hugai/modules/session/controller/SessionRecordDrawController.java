package com.hugai.modules.session.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.session.convert.SessionRecordDrawConvert;
import com.hugai.common.modules.entity.session.dto.SessionRecordDrawDTO;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.core.function.OR;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SESSION + "/sessionrecorddraw")
@Api(value = "SessionRecordDrawModel", tags = "绘图会话记录")
public class SessionRecordDrawController extends BaseController<SessionRecordDrawService, SessionRecordDrawModel> {

    private final UserInfoService userInfoService;

    @Override
    protected Result baseQueryPageByParam(@RequestBody SessionRecordDrawModel param) {
        IPage<SessionRecordDrawModel> page = service.listPageByParam(PageUtil.pageBean(param), param);
        IPage<SessionRecordDrawDTO> dtoPage = PageUtil.convert(page, SessionRecordDrawConvert.INSTANCE::convertToDTO);
        OR.run(dtoPage.getRecords(), CollUtil::isNotEmpty, list -> {
            List<Long> userIds = list.stream().map(SessionRecordDrawDTO::getUserId).distinct().collect(Collectors.toList());
            List<UserInfoModel> userList = userInfoService.lambdaQuery().in(UserInfoModel::getId, userIds).list();
            list.forEach(item -> {
                UserInfoModel userInfoModel = userList.stream().filter(user -> user.getId().equals(item.getUserId())).findFirst().orElseGet(UserInfoModel::new);
                item.setUserName(userInfoModel.getUserName());
            });
        });
        return Result.success(dtoPage);
    }
}
