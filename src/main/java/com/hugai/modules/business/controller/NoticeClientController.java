package com.hugai.modules.business.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.business.entity.model.NoticeClientModel;
import com.hugai.modules.business.service.NoticeClientService;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知公告
 *
 * @author WuHao
 * @since 2023/7/1 11:27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.BUSINESS + "/noticeclient")
@Api(value = "NoticeClient", tags = "通知公告")
public class NoticeClientController extends BaseController<NoticeClientService, NoticeClientModel> {

    @ApiOperation(value = "获取最新的一条通告信息")
    @GetMapping("/getLastNotice")
    public Result getLastNotice() {
        NoticeClientModel one = service.lambdaQuery()
                .eq(NoticeClientModel::getNoticeType,"NOTIFY")
                .orderByAsc(NoticeClientModel::getSort).last("limit 1")
                .one();
        return Result.success(one);
    }

}
