package com.hugai.modules.system.controller.monitor;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.SecurityConstant;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.system.entity.convert.SysUserTokenConvert;
import com.hugai.modules.system.entity.dto.SysUserTokenDTO;
import com.hugai.modules.system.entity.model.SysUserModel;
import com.hugai.modules.system.entity.model.SysUserTokenModel;
import com.hugai.modules.system.service.ISysUserService;
import com.hugai.modules.system.service.ISysUserTokenService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 在线用户监控
 *
 * @author wuhao
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/monitor/online")
@Api(tags = "在线用户监控")
public class SysUserOnlineController extends BaseController<ISysUserTokenService, SysUserTokenModel> {

    @Resource
    private RedisUtil redisUtil;

    private final ISysUserService sysUserService;

    @Override
    protected Result baseQueryPageByParam(@RequestBody SysUserTokenModel param) {
        IPage<SysUserTokenModel> result = service.listPageByParam(PageUtil.pageBean(param), param);
        IPage<SysUserTokenDTO> page = PageUtil.convert(result, SysUserTokenConvert.INSTANCE::convertToDTO);
        OR.run(page.getRecords(), CollUtil::isNotEmpty, list -> {
            List<Long> userIds = list.stream().map(SysUserTokenDTO::getUserId).distinct().collect(Collectors.toList());
            List<SysUserModel> userList = sysUserService.lambdaQuery().in(SysUserModel::getId, userIds).list();
            list.forEach(item -> {
                item.setSysUserModel(
                        OptionalUtil.ofNullList(userList).stream().filter(Objects::nonNull).filter(userModel -> userModel.getId().equals(item.getUserId())).findFirst().orElse(null)
                );
                String finalToken = SecurityConstant.LOGIN_TOKEN_KEY + item.getToken();
                item.setLoginUser(redisUtil.getCacheObject(finalToken));
            });
        });
        return Result.success(page);
    }

    @Log(title = "强退用户")
    @Override
    protected Result baseDeleteByIds(@PathVariable("ids") String ids) {
        List<Long> idList = StringUtils.splitToList(ids, Long::valueOf);
        List<SysUserTokenModel> list = service.lambdaQuery().in(SysUserTokenModel::getId, idList).list();
        OptionalUtil.ofNullList(list).forEach(item -> {
            if (service.removeById(item.getId())) {
                redisUtil.deleteObject(SecurityConstant.LOGIN_TOKEN_KEY + item.getToken());
            }
        });
        return Result.success();
    }
}
