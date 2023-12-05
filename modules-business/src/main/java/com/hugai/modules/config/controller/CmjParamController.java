package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.framework.log.annotation.Log;
import com.hugai.common.modules.entity.config.model.CmjParamModel;
import com.hugai.modules.config.service.ICmjParamService;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mj参数配置 控制器
 *
 * @author wuhao
 * @date 2023-09-25
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/cmjparam")
@Api(value = "CmjParamModel", tags = "mj参数配置")
public class CmjParamController extends BaseController<ICmjParamService, CmjParamModel> {

    @ApiOperation(value = "刷新mj参数列表缓存")
    @Log(title = "刷新mj参数列表缓存")
    @GetMapping("/flushCache")
    public Result flushCache() {
        service.cacheClear();
        return Result.success();
    }

}
