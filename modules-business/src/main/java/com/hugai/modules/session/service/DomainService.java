package com.hugai.modules.session.service;

import com.hugai.common.modules.entity.session.model.DomainModel;
import com.org.bebas.mapper.service.IService;

public interface DomainService extends IService<DomainModel> {

    /**
     * 通过唯一标识获取model
     * @param key
     * @return
     */
    DomainModel getByUniqueKey(String key);

}
