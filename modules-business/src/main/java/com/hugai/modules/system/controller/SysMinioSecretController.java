package com.hugai.modules.system.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.system.model.SysMinioSecretModel;
import com.hugai.modules.system.service.SysMinioSecretService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuHao
 * @since 2023/10/4 15:30
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/sysminiosecret")
@Api(value = "SysMinioSecretModel", tags = "minio配置")
public class SysMinioSecretController extends BaseController<SysMinioSecretService, SysMinioSecretModel> {
}
