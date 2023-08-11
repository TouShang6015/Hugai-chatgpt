package com.hugai.modules.session.controller;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.Constants;
import com.hugai.modules.session.entity.model.DomainModel;
import com.hugai.modules.session.service.DomainService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.utils.result.ResultUtil;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/6/23 17:12
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SESSION + "/domain")
@Api(value = "DomainModel", tags = "领域会话")
public class DomainController extends BaseController<DomainService, DomainModel> {

    @GetMapping("/getWindowData/{domainKey}")
    public Result getWindowData(@PathVariable String domainKey){
        DomainModel one = service.lambdaQuery()
                .eq(DomainModel::getUniqueKey, domainKey)
                .eq(DomainModel::getIfShow, Constants.BOOLEAN.TRUE)
                .one();
        Assert.notNull(one,() -> new BusinessException("不存在的场景会话类型"));
        String windowData = one.getWindowData();
        return Result.success(JSON.parseObject(windowData));
    }

    @Override
    protected Result baseQueryByParam(@RequestBody DomainModel param) {
        Result result = super.baseQueryByParam(param);
        List<DomainModel> list = ResultUtil.getData(result);
        OptionalUtil.ofNullList(list).forEach(item -> {
            item.setAboveContent(null);
            item.setFirstContent(null);
        });
        return result;
    }
}
