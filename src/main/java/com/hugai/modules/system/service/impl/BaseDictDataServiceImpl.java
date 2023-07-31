package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.modules.system.entity.model.BaseDictDataModel;
import com.hugai.modules.system.mapper.BaseDictDataMapper;
import com.hugai.modules.system.service.IBaseDictDataService;
import com.org.bebas.constants.RedisConstant;
import com.org.bebas.core.label.LabelBuilder;
import com.org.bebas.core.label.LabelOption;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典数据表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
@Service
public class BaseDictDataServiceImpl extends ServiceImpl<BaseDictDataMapper, BaseDictDataModel> implements IBaseDictDataService {

    private final String KEY_LIST = ModelUtil.modelMainKey(BaseDictDataModel.class) + RedisConstant.Keyword.LIST_PARAM;

    private final String KEY_KEYWORD = ModelUtil.modelMainKey(BaseDictDataModel.class) + RedisConstant.Keyword.ID;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取下拉
     *
     * @return
     */
    @Override
    public List<LabelOption<String, String>> optionSelect(String dictType) {
        List<BaseDictDataModel> list = this.selectListByDictType(dictType);
        return LabelBuilder.setValue(list).select(BaseDictDataModel::getDictLabel, BaseDictDataModel::getDictValue).build();
    }

    /**
     * 通过字典类型跟值获取对象
     *
     * @param dictType
     * @param dictValue
     * @return
     */
    @Override
    public BaseDictDataModel selectOneByTypeAndValue(@NotNull(message = "字典类型不能为空") String dictType, @NotNull(message = "字典值不能为空") String dictValue) {
        List<BaseDictDataModel> list = this.selectListByDictType(dictType);
        if (!CollUtil.isEmpty(list)) {
            return list.parallelStream().filter(item -> item.getDictValue().equals(dictValue)).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * 通过字典类型获取列表
     *
     * @param dictType
     * @return
     */
    @Override
    public List<BaseDictDataModel> selectListByDictType(String dictType) {
        List<BaseDictDataModel> list = redisUtil.getCacheList(KEY_LIST + dictType);
        if (CollUtil.isEmpty(list)) {
            list = super.listByParam(BaseDictDataModel.builder().dictType(dictType).build());
            if (!CollUtil.isEmpty(list))
                redisUtil.setCacheList(KEY_LIST + dictType, list);
        }
        return list;
    }

    /**
     * 根据id获取单个结果
     *
     * @param id
     * @return
     */
    @Override
    public BaseDictDataModel selectOneById(Long id) {
        return super.getById(id);
    }

    /**
     * 修改
     *
     * @param param
     * @return
     */
    @Override
    public boolean updateByParam(BaseDictDataModel param) {
        boolean result = super.updateById(param);
        if (result) {
            redisUtil.deleteObject(KEY_LIST + param.getDictType());
        }
        return result;
    }

    /**
     * 新增
     *
     * @param param
     * @return
     */
    @Override
    public boolean insertByParam(BaseDictDataModel param) {
        if (super.save(param)) {
            this.flushCache(param.getDictType());
        }
        return true;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(List<Long> ids) {
        // 删除缓存
        Optional.ofNullable(ids)
                .flatMap(idsList ->
                        Optional.of(idsList.parallelStream().map(this::selectOneById).map(BaseDictDataModel::getDictType).distinct().collect(Collectors.toList()))
                )
                .ifPresent(dictTypeList -> {
                    dictTypeList.parallelStream().forEach(this::flushCache);
                    super.removeBatchByIds(ids);
                });
        return 1;
    }

    /**
     * 刷新缓存
     *
     * @param dictType
     */
    @Override
    public void flushCache(String dictType) {
        redisUtil.deleteObject(KEY_LIST + dictType);
    }

    /**
     * 刷新缓存
     */
    @Override
    public void flushCache() {
        redisUtil.deleteLike(ModelUtil.modelMainKey(BaseDictDataModel.class) + "*");
    }
}
