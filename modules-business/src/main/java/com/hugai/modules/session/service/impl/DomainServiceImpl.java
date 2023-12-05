package com.hugai.modules.session.service.impl;

import com.hugai.common.modules.entity.session.model.DomainModel;
import com.hugai.modules.session.mapper.DomainMapper;
import com.hugai.modules.session.service.DomainService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class DomainServiceImpl extends ServiceImpl<DomainMapper, DomainModel> implements DomainService {

    @Override
    public DomainModel getByUniqueKey(String key) {
        DomainModel one = super.lambdaQuery().eq(DomainModel::getUniqueKey, key).one();
        return one;
    }
}
