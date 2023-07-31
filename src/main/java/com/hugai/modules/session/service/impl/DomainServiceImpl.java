package com.hugai.modules.session.service.impl;

import com.hugai.modules.session.entity.model.DomainModel;
import com.hugai.modules.session.mapper.DomainMapper;
import com.hugai.modules.session.service.DomainService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class DomainServiceImpl extends ServiceImpl<DomainMapper, DomainModel> implements DomainService {

}
