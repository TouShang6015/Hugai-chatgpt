package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * apikeys 控制器（后台）
 *
 * @author WuHao
 * @date 2023-05-26
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/admin/openaikeys")
@Api(value = "OpenaiKeysModel（后台）", tags = "apikeys（后台）")
public class OpenaiKeysAdminController extends BaseController<IOpenaiKeysService,OpenaiKeysModel> {

}
