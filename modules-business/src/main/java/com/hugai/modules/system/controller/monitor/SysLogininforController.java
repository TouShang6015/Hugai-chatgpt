package com.hugai.modules.system.controller.monitor;

import com.hugai.common.constants.ApiPrefixConstant;
import com.org.bebas.web.BaseController;
import com.hugai.modules.system.service.ISysLogininforService;
import com.hugai.common.modules.entity.system.model.SysLogininforModel;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 系统访问记录 控制器
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/syslogininfor")
@Api(value = "SysLogininforModel", tags = "系统访问记录- 控制器")
public class SysLogininforController extends BaseController<ISysLogininforService, SysLogininforModel> {

    @DeleteMapping("/{infoIds}")
    public Result remove(@PathVariable Long[] infoIds) {
        return Result.success(service.removeByIds(Arrays.asList(infoIds)));
    }

    @DeleteMapping("/clean")
    public Result clean() {
        service.clean();
        return Result.success();
    }

}
