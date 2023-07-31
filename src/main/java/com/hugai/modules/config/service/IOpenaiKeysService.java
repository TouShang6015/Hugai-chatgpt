package com.hugai.modules.config.service;

import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * apikeys 业务接口
 *
 * @author WuHao
 * @date 2023-05-26
 */
public interface IOpenaiKeysService extends IService<OpenaiKeysModel> {

    String OPENAI_KEYS_CACHE_KEY = "openai_keys:";

    /**
     * 获取可用的key
     *
     * @return
     */
    List<String> getAbleKeys();

    /**
     * 获取公共key
     *
     * @return
     */
    List<OpenaiKeysModel> getCommonKey();

    /**
     * 新增系统key
     *
     * @param param
     */
    void saveByCommon(OpenaiKeysModel param);

    /**
     * 获取用户的key
     *
     * @param userId
     * @return
     */
    List<OpenaiKeysModel> getByUser(Long userId);

    /**
     * 新增用户key
     *
     * @param param
     */
    void saveByUser(OpenaiKeysModel param);

    /**
     * 删除用户key
     *
     * @param id
     */
    void removeKeys(Long id);

    /**
     * 修改
     *
     * @param param
     */
    void updateKeys(OpenaiKeysModel param);

}
