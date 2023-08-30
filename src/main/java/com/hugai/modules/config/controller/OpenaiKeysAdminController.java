package com.hugai.modules.config.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.utils.result.ResultUtil;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
public class OpenaiKeysAdminController extends BaseController<IOpenaiKeysService, OpenaiKeysModel> {

    @Resource
    private RedisUtil redisUtil;

    @Override
    protected <DTO> Result baseAdd(@RequestBody DTO m) {
        Result result = super.baseAdd(m);
        if (ResultUtil.verifySuccess(result)) {
            redisUtil.deleteObject(IOpenaiKeysService.OPENAI_KEYS_CACHE_KEY + "common");
        }
        return result;
    }

    @Override
    protected Result baseDeleteByIds(@PathVariable("ids") String ids) {
        Result result = super.baseDeleteByIds(ids);
        if (ResultUtil.verifySuccess(result)) {
            redisUtil.deleteObject(IOpenaiKeysService.OPENAI_KEYS_CACHE_KEY + "common");
        }
        return result;
    }
}
