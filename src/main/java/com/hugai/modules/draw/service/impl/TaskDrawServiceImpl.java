package com.hugai.modules.draw.service.impl;

import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.mapper.TaskDrawMapper;
import com.hugai.modules.draw.service.TaskDrawService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author WuHao
 * @since 2023/9/7 17:09
 */
@Service
public class TaskDrawServiceImpl extends ServiceImpl<TaskDrawMapper, TaskDrawModel> implements TaskDrawService {
}
