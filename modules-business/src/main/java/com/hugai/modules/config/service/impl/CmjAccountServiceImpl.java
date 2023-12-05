package com.hugai.modules.config.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hugai.common.enums.flow.AccountStatus;
import com.hugai.common.modules.entity.config.convert.CmjAccountConvert;
import com.hugai.common.modules.entity.config.model.CmjAccountModel;
import com.hugai.common.modules.entity.config.model.CmjChannelConfigModel;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
import com.hugai.modules.config.mapper.CmjAccountMapper;
import com.hugai.modules.config.mapper.CmjChannelConfigMapper;
import com.hugai.modules.config.service.ICmjAccountService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * mj账户配置 业务实现类
 *
 * @author wuhao
 * @date 2023-09-25
 */
@Service
public class CmjAccountServiceImpl extends ServiceImpl<CmjAccountMapper, CmjAccountModel> implements ICmjAccountService {

    @Resource
    private CmjChannelConfigMapper channelConfigMapper;

    @Override
    public List<CmjAccountDetailVO> getAccountAll() {
        List<CmjAccountModel> accountModelList = this.list();
        if (CollUtil.isEmpty(accountModelList)) {
            return null;
        }
        List<CmjAccountModel> filterNormalAccountList = accountModelList.stream().filter(item -> AccountStatus.NORMAL.getKey().equals(item.getAccountStatus())).collect(Collectors.toList());
        List<CmjChannelConfigModel> channelConfigModelList = channelConfigMapper.selectList(null);
        return filterNormalAccountList.stream().map(accountModel -> {
            CmjAccountDetailVO vo = CmjAccountConvert.INSTANCE.convertDetailVo(accountModel);
            List<CmjChannelConfigModel> channelConfigList = channelConfigModelList.stream().filter(channelConfig -> accountModel.getId().equals(channelConfig.getCmjAccountId())).collect(Collectors.toList());
            vo.setChannelConfigList(channelConfigList);
            vo.setChannelIds(channelConfigList.stream().map(CmjChannelConfigModel::getChannelId).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean save(CmjAccountModel entity) {
        Assert.isFalse(
                super.lambdaQuery()
                        .eq(CmjAccountModel::getUserName, entity.getUserName())
                        .eq(CmjAccountModel::getUserToken, entity.getUserToken())
                        .count() > 0,
                () -> new BusinessException("账户名称或Token重复")
        );
        entity.setAccountStatus(AccountStatus.NORMAL.getKey());
        return super.save(entity);
    }

    @Override
    public boolean updateById(CmjAccountModel entity) {
        Assert.isFalse(
                super.lambdaQuery()
                        .ne(CmjAccountModel::getId, entity.getId())
                        .eq(CmjAccountModel::getUserName, entity.getUserName())
                        .eq(CmjAccountModel::getUserToken, entity.getUserToken())
                        .count() > 0,
                () -> new BusinessException("账户名称或Token重复")
        );
        return super.updateById(entity);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        if (super.removeByIds(list)) {
            channelConfigMapper.delete(Wrappers.<CmjChannelConfigModel>lambdaQuery().in(CmjChannelConfigModel::getCmjAccountId, list));
        }
        return true;
    }
}
