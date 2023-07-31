package com.hugai.modules.system.controller.monitor;

import com.hugai.common.constants.ApiPrefixConstant;
import com.org.bebas.web.BaseController;
import com.hugai.modules.system.service.ISysOperLogService;
import com.hugai.modules.system.entity.model.SysOperLogModel;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志记录 控制器
 *
 * @author WuHao
 * @date 2022-06-22 22:35:41
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/sysoperlog")
@Api(value = "SysOperLogModel", tags = "操作日志记录- 控制器")
public class SysOperLogController extends BaseController<ISysOperLogService, SysOperLogModel> {

    @DeleteMapping("/clean")
    public Result cleanOperlog() {
        service.cleanOperlog();
        return Result.success();
    }

}
