package com.hugai.modules.config.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.Constants;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.hugai.modules.config.mapper.OpenaiKeysMapper;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.core.validator.group.GroupInsert;
import com.org.bebas.core.validator.group.GroupUpdate;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * apikeys 业务实现类
 *
 * @author WuHao
 * @date 2023-05-26
 */
@Service
public class OpenaiKeysServiceImpl extends ServiceImpl<OpenaiKeysMapper, OpenaiKeysModel> implements IOpenaiKeysService {

    @Resource
    private IBaseResourceConfigService resourceConfigService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 获取可用的key
     *
     * @return
     */
    @Override
    public List<String> getAbleKeys(Long userId) {
        UserInfoModel userInfoModel = userInfoService.getById(userId);
        ResourceMainVO resourceMain = resourceConfigService.getResourceMain();

        List<OpenaiKeysModel> commonKeyModelList = this.getCommonKey();
        List<OpenaiKeysModel> usableCommonModelList = OptionalUtil.ofNullList(commonKeyModelList).stream()
                .filter(item -> Constants.EnableStatus.USABLE.equals(item.getEnableStatus())).collect(Collectors.toList());


        List<String> finalKeys;
        // 普通用户

        if (Objects.nonNull(userInfoModel)) {
            List<OpenaiKeysModel> userModelList = this.getByUser(userId);
            List<OpenaiKeysModel> usableUserModelList = OptionalUtil.ofNullList(userModelList).stream().filter(item -> Constants.EnableStatus.USABLE.equals(item.getEnableStatus())).collect(Collectors.toList());
            // 不开启系统key
            if (!resourceMain.getAbleSystemApiKey()) {
                Assert.notEmpty(usableUserModelList, () -> new BusinessException("openai key为空，请前往openAi官网获取apiKey"));
                finalKeys = usableUserModelList.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
            } else {        // 开启系统key
                if (CollUtil.isEmpty(usableUserModelList)) {
                    finalKeys = usableCommonModelList.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
                } else {
                    finalKeys = usableUserModelList.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
                }
            }
        } else {  // 系统用户
            Assert.notEmpty(usableCommonModelList, () -> new BusinessException("openai key为空，请前往openAi官网获取apiKey"));
            finalKeys = usableCommonModelList.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
        }
        log.info("用户id：[{}] , 获取可用apiKey: {}", userId, JSON.toJSONString(finalKeys));
        return finalKeys;
    }

    /**
     * 获取用户可用的key，不包括公共key
     *
     * @return
     */
    @Override
    public List<String> getUserAbleKeys(Long userId) {

        List<OpenaiKeysModel> userModelList = this.getByUser(userId);
        List<OpenaiKeysModel> usableUserModelList = OptionalUtil.ofNullList(userModelList).stream().filter(item -> Constants.EnableStatus.USABLE.equals(item.getEnableStatus())).collect(Collectors.toList());

        return usableUserModelList.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
    }

    /**
     * 获取公共key
     *
     * @return
     */
    @Override
    public List<OpenaiKeysModel> getCommonKey() {
        String key = OPENAI_KEYS_CACHE_KEY + "common";
        List<OpenaiKeysModel> cacheList = redisUtil.getCacheList(key);
        if (CollUtil.isEmpty(cacheList)) {
            cacheList = super.lambdaQuery().eq(OpenaiKeysModel::getIfCommon, Constants.BOOLEAN.TRUE).list();
            OR.run(cacheList, CollUtil::isNotEmpty, list -> redisUtil.setCacheList(key, list));
        }
        return cacheList;
    }

    /**
     * 新增系统key
     *
     * @param param
     */
    @Override
    public void saveByCommon(OpenaiKeysModel param) {
        ValidatorUtil.validateEntity(param, GroupInsert.class);
        param.setEnableStatus(Constants.EnableStatus.USABLE);
        param.setIfCommon(Constants.BOOLEAN.TRUE);
        if (super.save(param)) {
            String key = OPENAI_KEYS_CACHE_KEY + "common";
            redisUtil.deleteObject(key);
            List<OpenaiKeysModel> list = super.lambdaQuery().eq(OpenaiKeysModel::getIfCommon, Constants.BOOLEAN.TRUE).list();
            redisUtil.setCacheList(key, list);
        }
    }

    /**
     * 获取用户的key
     *
     * @param userId
     * @return
     */
    @Override
    public List<OpenaiKeysModel> getByUser(Long userId) {
        String key = OPENAI_KEYS_CACHE_KEY + "user:" + userId;
        List<OpenaiKeysModel> cacheList = redisUtil.getCacheList(key);
        if (CollUtil.isEmpty(cacheList)) {
            cacheList = super.lambdaQuery().eq(OpenaiKeysModel::getUserId, userId).list();
            OR.run(cacheList, CollUtil::isNotEmpty, list -> redisUtil.setCacheList(key, list));
        }
        return cacheList;
    }

    /**
     * 新增用户key
     *
     * @param param
     */
    @Override
    public void saveByUser(OpenaiKeysModel param) {
        ValidatorUtil.validateEntity(param, GroupInsert.class);
        String apiKey = param.getApiKey();

        Long userId = SecurityContextUtil.getUserId();
        Assert.isFalse(
                super.lambdaQuery().eq(OpenaiKeysModel::getUserId, userId).eq(OpenaiKeysModel::getApiKey, apiKey).count() > 0,
                () -> new BusinessException("存在重复的openai key，请重新输入")
        );

        param.setUserId(userId);
        param.setEnableStatus(Constants.EnableStatus.USABLE);
        param.setIfCommon(Constants.BOOLEAN.FALSE);
        if (super.save(param)) {
            String key = OPENAI_KEYS_CACHE_KEY + "user:" + userId;
            redisUtil.deleteObject(key);
            List<OpenaiKeysModel> list = super.lambdaQuery()
                    .eq(OpenaiKeysModel::getUserId, userId)
                    .eq(OpenaiKeysModel::getIfCommon, Constants.BOOLEAN.FALSE)
                    .eq(OpenaiKeysModel::getEnableStatus, Constants.EnableStatus.USABLE)
                    .list();
            redisUtil.setCacheList(key, list);
        }
    }

    /**
     * 删除用户key
     *
     * @param id
     */
    @Override
    public void removeKeys(Long id) {
        OpenaiKeysModel userOpenKeyModel = super.lambdaQuery().eq(OpenaiKeysModel::getId, id).eq(OpenaiKeysModel::getUserId, SecurityContextUtil.getUserId()).one();
        OR.run(userOpenKeyModel, Objects::nonNull, keysModel -> {
            if (super.removeById(id)) {
                Long userId = keysModel.getUserId();
                if (Objects.nonNull(userId)) {
                    String key = OPENAI_KEYS_CACHE_KEY + "user:" + userId;
                    redisUtil.deleteObject(key);
                } else {
                    String key = OPENAI_KEYS_CACHE_KEY + "common";
                    redisUtil.deleteObject(key);
                }
            }
        });
    }

    /**
     * 修改
     *
     * @param param
     */
    @Override
    public void updateKeys(OpenaiKeysModel param) {
        ValidatorUtil.validateEntity(param, GroupUpdate.class);

        Long id = param.getId();
        String apiKey = param.getApiKey();
        Assert.notEmpty(apiKey, () -> new BusinessException("必要参数不能为空"));

        Long userId = SecurityContextUtil.getUserId();
        Assert.isFalse(
                super.lambdaQuery().eq(OpenaiKeysModel::getUserId, userId).eq(OpenaiKeysModel::getApiKey, apiKey).ne(OpenaiKeysModel::getId, param.getId()).count() > 0,
                () -> new BusinessException("存在重复的openai key，请重新输入")
        );

        OpenaiKeysModel userOpenKeyModel = super.lambdaQuery().eq(OpenaiKeysModel::getId, id).eq(OpenaiKeysModel::getUserId, userId).one();
        OR.run(userOpenKeyModel, Objects::nonNull, keysModel -> {
            param.setIfCommon(null);
            if (super.updateById(param)) {
                if (Objects.nonNull(userId)) {
                    String key = OPENAI_KEYS_CACHE_KEY + "user:" + userId;
                    redisUtil.deleteObject(key);
                } else {
                    String key = OPENAI_KEYS_CACHE_KEY + "common";
                    redisUtil.deleteObject(key);
                }
            }
        });
    }

    /**
     * 根据openaikey删除
     *
     * @param openAiKeys
     */
    @Override
    public void removeByOpenaiKey(List<String> openAiKeys) {
        if (CollUtil.isEmpty(openAiKeys)) {
            return;
        }
        List<OpenaiKeysModel> openaiKeysModelList = super.lambdaQuery().in(OpenaiKeysModel::getApiKey, openAiKeys).list();
        if (CollUtil.isEmpty(openaiKeysModelList)) {
            return;
        }
        List<Long> userIds = openaiKeysModelList.stream().map(OpenaiKeysModel::getUserId).distinct().collect(Collectors.toList());

        List<OpenaiKeysModel> updateParam = openaiKeysModelList.stream().peek(item -> item.setEnableStatus(Constants.EnableStatus.DISABLE)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(updateParam)) {
            super.updateBatchById(updateParam);
            // 删除缓存
            // 公共key
            List<OpenaiKeysModel> commonKeys = openaiKeysModelList.stream().filter(item -> Constants.BOOLEAN.TRUE.equals(item.getIfCommon())).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(commonKeys)) {
                String key = OPENAI_KEYS_CACHE_KEY + "common";
                redisUtil.deleteObject(key);
            }
            // 用户key
            if (CollUtil.isNotEmpty(userIds)) {
                List<String> cacheKeys = userIds.stream().distinct().map(userId -> OPENAI_KEYS_CACHE_KEY + "user:" + userId).collect(Collectors.toList());
                redisUtil.deleteObject(cacheKeys);
            }
            log.info("openai key 无用key刷新：{}", JSON.toJSONString(openAiKeys));
        }
    }

}
