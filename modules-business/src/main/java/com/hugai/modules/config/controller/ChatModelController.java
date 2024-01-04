package com.hugai.modules.config.controller;

import cn.hutool.core.map.MapUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.Constants;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.modules.config.service.IChatModelService;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对话模型管理 控制器
 *
 * @author wuhao
 * @date 2023-11-27
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/chatmodel")
@Api(value = "ChatModelModel", tags = "对话模型管理")
public class ChatModelController extends BaseController<IChatModelService, ChatModelModel> {

    @ApiOperation(value = "获取下拉")
    @GetMapping("/getLabelOption")
    public Result getLabelOption() {
        List<ChatModelModel> allList = service.getAllList();
        List<Map<String, Object>> labelOptions = OptionalUtil.ofNullList(allList).stream()
                .filter(item -> Constants.EnableStatus.USABLE.equals(item.getEnableStatus()))
                .map(item -> {
                    HashMap<String, Object> map = MapUtil.newHashMap();
                    map.put("value", item.getId());
                    map.put("label", item.getModelDescription());
                    map.put("ifChatPlus", item.getIfPlusModel());
                    return map;
                }).collect(Collectors.toList());
        return Result.success(labelOptions);
    }

    @ApiOperation(value = "刷新缓存")
    @GetMapping("/flushCache")
    public void flushCache() {
        service.flushCache();
    }


}
