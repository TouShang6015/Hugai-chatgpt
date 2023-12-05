package com.hugai.modules.business.service.impl;

import com.hugai.common.modules.entity.business.model.NoticeClientModel;
import com.hugai.modules.business.mapper.NoticeClientMapper;
import com.hugai.modules.business.service.NoticeClientService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * NoticeClient 业务实现类
 *
 * @author WuHao
 * @date 2023-05-26
 */
@Service
public class NoticeClientServiceImpl extends ServiceImpl<NoticeClientMapper, NoticeClientModel> implements NoticeClientService {

}
