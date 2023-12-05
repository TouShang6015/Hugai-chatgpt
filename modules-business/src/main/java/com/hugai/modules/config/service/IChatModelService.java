package com.hugai.modules.config.service;

import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 对话模型管理 业务接口
 *
 * @author wuhao
 * @date 2023-11-27
 */
public interface IChatModelService extends IService<ChatModelModel> {

    ChatModelModel getByUniqueKey(String key);

    List<ChatModelModel> getAllList();

    void flushCache();

}
