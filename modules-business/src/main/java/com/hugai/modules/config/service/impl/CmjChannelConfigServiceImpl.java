package com.hugai.modules.config.service.impl;

import com.hugai.common.modules.entity.config.model.CmjChannelConfigModel;
import com.hugai.modules.config.mapper.CmjChannelConfigMapper;
import com.hugai.modules.config.service.ICmjChannelConfigService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * mj频道配置 业务实现类
 *
 * @author wuhao
 * @date 2023-09-25
 */
@Service
public class CmjChannelConfigServiceImpl extends ServiceImpl<CmjChannelConfigMapper, CmjChannelConfigModel> implements ICmjChannelConfigService {

}
