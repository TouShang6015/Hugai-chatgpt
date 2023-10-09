package com.hugai.modules.midjourney.service.impl;

import com.hugai.modules.midjourney.entity.model.CmjChannelConfigModel;
import com.hugai.modules.midjourney.mapper.CmjChannelConfigMapper;
import com.hugai.modules.midjourney.service.ICmjChannelConfigService;
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
