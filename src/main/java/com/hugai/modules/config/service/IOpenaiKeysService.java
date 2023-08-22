package com.hugai.modules.config.service;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.security.context.SecurityContextUtil;
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
    default List<String> getAbleKeys() {
        return this.getAbleKeys(SecurityContextUtil.getUserId());
    }

    List<String> getAbleKeys(Long userId);

    /**
     * 获取用户可用的key，不包括公共key
     *
     * @return
     */
    List<String> getUserAbleKeys(Long userId);

    default List<String> getUserAbleKeys() {
        return getUserAbleKeys(SecurityContextUtil.getUserId());
    }

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

    /**
     * 根据openaikey删除
     *
     * @param openAiKeys
     */
    void removeByOpenaiKey(List<String> openAiKeys);

    default void removeByOpenaiKey(String openAiKey) {
        this.removeByOpenaiKey(CollUtil.newArrayList(openAiKey));
    }

}
