package com.hugai.modules.config.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.enums.flow.AccountStatus;
import com.hugai.common.modules.entity.config.convert.CmjAccountConvert;
import com.hugai.common.modules.entity.config.dto.CmjAccountDTO;
import com.hugai.common.modules.entity.config.model.CmjAccountModel;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
import com.hugai.core.midjourney.client.DiscordSocketClient;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.pool.DiscordAccountCacheObj;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import com.hugai.modules.config.service.ICmjAccountService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import com.org.bebas.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * mj账户配置 控制器
 *
 * @author wuhao
 * @date 2023-09-25
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.CONFIG + "/cmjaccount")
@Api(value = "CmjAccountModel", tags = "mj账户配置")
public class CmjAccountController extends BaseController<ICmjAccountService, CmjAccountModel> {

    @ApiOperation(value = "Discord账户重新连接")
    @GetMapping("/againConnect/{id}")
    public Result againConnect(@PathVariable Long id) {
        List<CmjAccountDetailVO> accountAll = service.getAccountAll();

        CmjAccountDetailVO accountDetailVO = accountAll.stream().filter(item -> id.equals(item.getId())).findFirst().orElse(null);
        Assert.notNull(accountDetailVO, () -> new BusinessException("未找到该账户"));

        Assert.isFalse(!AccountStatus.NORMAL.getKey().equals(accountDetailVO.getAccountStatus()), () -> new BusinessException("账户已停用"));

        DiscordAccountCacheObj discordAccountCacheObj = DiscordSocketAccountPool.get(accountDetailVO.getUserName());
        if (Objects.nonNull(discordAccountCacheObj)) {
            discordAccountCacheObj.getWebSocket().cancel();
        }
        DiscordSocketClient.getDiscordAccountMap().remove(accountDetailVO.getUserName());

        DiscordAccount discordAccount = DiscordSocketClient.buildAccount(accountDetailVO);
        DiscordSocketClient.connection(discordAccount);
        return Result.success();
    }

    @ApiOperation(value = "Discord账户断开连接")
    @GetMapping("/closeConnect/{id}")
    public Result closeConnect(@PathVariable Long id) {
        CmjAccountModel model = service.getById(id);
        Assert.notNull(model, () -> new BusinessException("未找到账户"));

        List<CmjAccountDetailVO> accountAll = service.getAccountAll();

        CmjAccountDetailVO accountDetailVO = accountAll.stream().filter(item -> id.equals(item.getId())).findFirst().orElse(null);
        Assert.notNull(accountDetailVO, () -> new BusinessException("未找到该账户"));

        DiscordAccountCacheObj discordAccountCacheObj = DiscordSocketAccountPool.get(model.getUserName());
        if (Objects.nonNull(discordAccountCacheObj)) {
            discordAccountCacheObj.getWebSocket().cancel();
        }
        Map<String, DiscordAccount> discordAccountMap = DiscordSocketClient.getDiscordAccountMap();
        discordAccountMap.remove(accountDetailVO.getUserName());
        return Result.success();
    }

    @ApiOperation(value = "刷新缓存")
    @GetMapping("/flushCache")
    public Result flushCache() {
        service.flushCache();
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
