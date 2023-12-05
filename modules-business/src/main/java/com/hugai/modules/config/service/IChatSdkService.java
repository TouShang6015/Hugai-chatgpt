package com.hugai.modules.config.service;

import com.hugai.common.modules.entity.config.model.ChatSdkModel;
import com.org.bebas.mapper.service.IService;

/**
 * 对话第三方平台管理 业务接口
 *
 * @author wuhao
 * @date 2023-11-27
 */
public interface IChatSdkService extends IService<ChatSdkModel> {

    ChatSdkModel getByUniqueKey(String key);

}
