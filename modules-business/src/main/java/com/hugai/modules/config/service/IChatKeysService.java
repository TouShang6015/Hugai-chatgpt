package com.hugai.modules.config.service;

import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 对话秘钥池 业务接口
 *
 * @author wuhao
 * @date 2023-11-27
 */
public interface IChatKeysService extends IService<ChatKeysModel> {

    List<ChatKeysModel> getAbleByChatSdkId(Long chatSdkId);

    void removeByApiToken(String token);

    void flushCache();

}
