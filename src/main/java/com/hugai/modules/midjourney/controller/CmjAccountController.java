package com.hugai.modules.midjourney.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.core.midjourney.client.DiscordSocketClient;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.pool.DiscordAccountCacheObj;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import com.hugai.modules.midjourney.entity.convert.CmjAccountConvert;
import com.hugai.modules.midjourney.entity.dto.CmjAccountDTO;
import com.hugai.modules.midjourney.entity.model.CmjAccountModel;
import com.hugai.modules.midjourney.service.ICmjAccountService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

/**
 * mj账户配置 控制器
 *
 * @author wuhao
 * @date 2023-09-25
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.MJ + "/cmjaccount")
@Api(value = "CmjAccountModel", tags = "mj账户配置")
public class CmjAccountController extends BaseController<ICmjAccountService, CmjAccountModel> {

    @ApiOperation(value = "Discord账户重新连接")
    @GetMapping("/againConnect/{id}")
    public Result againConnect(@PathVariable Long id){
        CmjAccountModel model = service.getById(id);
        Assert.notNull(model,() -> new BusinessException("未找到账户"));

        DiscordAccountCacheObj discordAccountCacheObj = DiscordSocketAccountPool.get(model.getUserName());
        if (Objects.nonNull(discordAccountCacheObj)){
            discordAccountCacheObj.getWebSocket().cancel();
        }
        Set<DiscordAccount> discordAccountList = DiscordSocketClient.getDiscordAccountList();
        DiscordAccount discordAccount = discordAccountList.stream().filter(item -> item.getUserName().equals(model.getUserName())).findFirst().orElse(null);
        Assert.notNull(discordAccount,() -> new BusinessException("未找到账户"));
        DiscordSocketClient.connection(discordAccount);
        return Result.success();
    }

    @Override
    protected Result baseQueryPageByParam(@RequestBody CmjAccountModel param) {
        IPage<CmjAccountModel> page = service.listPageByParam(PageUtil.pageBean(param), param);
        IPage<CmjAccountDTO> dtoPage = PageUtil.convert(page, CmjAccountConvert.INSTANCE::convertToDTO);
        OR.run(dtoPage.getRecords(), CollUtil::isNotEmpty, list -> {
            list.forEach(item -> {
                item.setSocketStatus(0);
                OR.run(DiscordSocketAccountPool.get(item.getUserName()), Objects::nonNull, discordAccountCacheObj -> {
                    item.setSocketStatus(1);
                });
            });
        });
        return Result.success(dtoPage);
    }
}
