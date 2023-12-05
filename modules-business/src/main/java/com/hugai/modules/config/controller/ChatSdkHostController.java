package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.config.model.ChatSdkHostModel;
import com.hugai.modules.config.service.IChatSdkHostService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对话镜像地址管理 控制器
 *
 * @author wuhao
 * @date 2023-11-27
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/chatsdkhost")
@Api(value = "ChatSdkHostModel", tags = "对话镜像地址管理")
public class ChatSdkHostController extends BaseController<IChatSdkHostService, ChatSdkHostModel> {

    @ApiOperation(value = "刷新缓存")
    @GetMapping("/flushCache")
    public void flushCache() {
        service.flushCache();
    }

}
