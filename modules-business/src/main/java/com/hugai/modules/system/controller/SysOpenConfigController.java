package com.hugai.modules.system.controller;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.system.model.SysOpenConfigModel;
import com.hugai.common.webApi.openCnofig.OpenConfigCacheFlush;
import com.hugai.modules.system.service.ISysOpenConfigService;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 第三方配置 控制器
 *
 * @author wuhao
 * @date 2023-12-20
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/sysopenconfig")
@Api(value = "SysOpenConfigModel", tags = "第三方配置")
public class SysOpenConfigController extends BaseController<ISysOpenConfigService, SysOpenConfigModel> {

    @Autowired
    private List<OpenConfigCacheFlush> openConfigCacheFlushes;

    @Override
    protected <DTO> Result baseEdit(@RequestBody DTO m) {
        SysOpenConfigModel param = JSON.parseObject(JSON.toJSONString(m), service.getModelClass());
        if (!service.updateById(param)) {
            return Result.fail(ResultEnum.FAIL_UPDATE);
        }
        openConfigCacheFlushes.forEach(openConfigCacheFlush -> {
            openConfigCacheFlush.flushCache(param.getUniqueKey());
        });
        return Result.success(ResultEnum.SUCCESS_UPDATE);
    }
}
