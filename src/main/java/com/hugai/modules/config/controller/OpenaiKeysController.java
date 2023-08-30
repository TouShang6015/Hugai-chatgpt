package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * apikeys 控制器
 *
 * @author WuHao
 * @date 2023-05-26
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/openaikeys")
@Api(value = "OpenaiKeysModel", tags = "apikeys")
public class OpenaiKeysController {

    private final IOpenaiKeysService service;

    @ApiOperation(value = "获取用户api keys")
    @GetMapping("/getUserApiKeys")
    public Result getUserApiKeys() {
        List<OpenaiKeysModel> list = service.lambdaQuery().eq(OpenaiKeysModel::getUserId, SecurityContextUtil.getUserId()).list();
        return Result.success(list);
    }

    @Log(title = "添加用户api")
    @ApiOperation(value = "添加用户api keys")
    @PostMapping("/addUserApiKey")
    public Result addUserApiKey(@RequestBody OpenaiKeysModel param) {
        service.saveByUser(param);
        return Result.success();
    }

    @Log(title = "删除用户api")
    @ApiOperation(value = "删除用户api keys")
    @DeleteMapping("/delUserApiKey/{id}")
    public Result delUserApiKey(@PathVariable Long id) {
        service.removeKeys(id);
        return Result.success();
    }

}
