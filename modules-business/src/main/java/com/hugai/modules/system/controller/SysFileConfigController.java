package com.hugai.modules.system.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.system.model.SysFileConfigModel;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件配置
 *
 * @author WuHao
 * @since 2023/7/1 11:27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/sysfileconfig")
@Api(value = "SysFileConfig", tags = "文件配置")
public class SysFileConfigController extends BaseController<SysFileConfigService, SysFileConfigModel> {

}
