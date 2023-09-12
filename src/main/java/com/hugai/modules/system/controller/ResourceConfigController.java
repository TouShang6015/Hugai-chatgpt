package com.hugai.modules.system.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.ResourceConfigConstant;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.system.entity.model.BaseResourceConfigModel;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数配置表 控制层
 *
 * @author WuHao
 * @date 2022/5/21 11:18
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/baseresourceconfig")
@Api(value = "BaseResourceConfigModel", tags = "系统参数配置")
public class ResourceConfigController {

    private final IBaseResourceConfigService service;

    @ApiOperation(value = "单独获取main配置信息", httpMethod = "GET", response = Result.class)
    @GetMapping("/configMain")
    public Result getConfigMain() {
        BaseResourceConfigModel model = service.queryByConfigKey(ResourceConfigConstant.MAIN_KEY);
        return Result.success(model);
    }

    @ApiOperation(value = "根据configKey获取信息", httpMethod = "GET", response = Result.class)
    @GetMapping("/queryByConfigKey/{configKey}")
    public Result queryByConfigKey(@PathVariable String configKey) {
        BaseResourceConfigModel model = service.queryByConfigKey(configKey);
        return Result.success(model);
    }

    @Log(title = "编辑系统参数配置信息")
    @ApiOperation(value = "根据configKey编辑信息", httpMethod = "PUT", response = Result.class)
    @PutMapping("/editByConfigKey")
    public Result editByConfigKey(@RequestBody BaseResourceConfigModel param) {
        if (!service.editByConfigKey(param))
            return Result.fail();
        return Result.success();
    }

}
