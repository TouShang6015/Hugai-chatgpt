package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.config.model.CmjChannelConfigModel;
import com.hugai.modules.config.service.ICmjChannelConfigService;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mj频道配置 控制器
 *
 * @author wuhao
 * @date 2023-09-25
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/cmjchannelconfig")
@Api(value = "CmjChannelModel", tags = "mj频道配置")
public class CmjChannelController extends BaseController<ICmjChannelConfigService, CmjChannelConfigModel> {

}
