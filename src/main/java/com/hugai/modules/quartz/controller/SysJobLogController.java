package com.hugai.modules.quartz.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.org.bebas.web.BaseController;
import com.hugai.modules.quartz.entity.model.SysJobLogModel;
import com.hugai.modules.quartz.service.ISysJobLogService;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务调度日志表 控制器
 *
 * @author WuHao
 * @date 2022-09-06 18:51:31
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.QUARTZ + "/sysjoblog")
@Api(value = "SysJobLogModel", tags = "定时任务调度日志表")
public class SysJobLogController extends BaseController<ISysJobLogService, SysJobLogModel> {

    @DeleteMapping("/clean")
    public Result clean() {
        service.cleanJobLog();
        return Result.success();
    }
}
