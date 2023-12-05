package com.hugai.modules.system.service.impl;

import com.hugai.common.modules.entity.system.model.SysAttachmentModel;
import com.hugai.modules.system.mapper.SysAttachmentMapper;
import com.hugai.modules.system.service.ISysAttachmentService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author WuHao
 * @since 2023/10/8 9:19
 */
@Service
public class SysAttachmentServiceImpl extends ServiceImpl<SysAttachmentMapper, SysAttachmentModel> implements ISysAttachmentService {
}
