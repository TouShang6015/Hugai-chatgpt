package com.hugai.modules.config.service;

import com.hugai.common.modules.entity.config.model.ChatSdkHostModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 对话镜像地址管理 业务接口
 *
 * @author wuhao
 * @date 2023-11-27
 */
public interface IChatSdkHostService extends IService<ChatSdkHostModel> {

    /**
     * 通过chatSdkId获取列表
     *
     * @param chatSdkId
     * @return
     */
    List<ChatSdkHostModel> getUsableByChatSdkId(Long chatSdkId);

    void flushCache();

}
