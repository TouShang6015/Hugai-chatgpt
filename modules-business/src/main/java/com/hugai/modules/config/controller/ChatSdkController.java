package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.hugai.modules.config.service.IChatSdkService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对话第三方平台管理 控制器
 *
 * @author wuhao
 * @date 2023-11-27
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/chatsdk")
@Api(value = "ChatSdkModel",tags = "对话第三方平台管理")
public class ChatSdkController extends BaseController<IChatSdkService, ChatSdkModel> {

}
