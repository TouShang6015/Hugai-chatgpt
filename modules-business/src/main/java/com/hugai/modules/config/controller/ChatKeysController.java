package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.modules.config.service.IChatKeysService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对话秘钥池 控制器
 *
 * @author wuhao
 * @date 2023-11-27
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/chatkeys")
@Api(value = "ChatKeysModel",tags = "对话秘钥池")
public class ChatKeysController extends BaseController<IChatKeysService, ChatKeysModel> {

    @ApiOperation(value = "刷新缓存")
    @GetMapping("/flushCache")
    public void flushCache(){
        service.flushCache();
    }

}
