package com.hugai.modules.system.service;

import com.hugai.modules.system.entity.model.BaseDictTypeModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 字典类型表 业务接口
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
public interface IBaseDictTypeService extends IService<BaseDictTypeModel> {


    /**
     * 根据字典类型获取
     *
     * @param dictType
     * @return
     */
    BaseDictTypeModel selectOneByType(String dictType);

    /**
     * 根据id获取单个结果
     *
     * @param id
     * @return
     */
    BaseDictTypeModel selectOneById(Long id);

    /**
     * 修改
     *
     * @param param
     * @return
     */
    boolean updateByParam(BaseDictTypeModel param);

    /**
     * 新增
     *
     * @param param
     * @return
     */
    boolean insertByParam(BaseDictTypeModel param);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);

    /**
     * 获取缓存通过类型
     *
     * @param dictType
     */
    BaseDictTypeModel cacheGetByDictType(String dictType);

    /**
     * 新增缓存通过类型
     *
     * @param param
     */
    void cacheAddByDictType(final BaseDictTypeModel param);

    /**
     * 删除缓存通过类型
     *
     * @param dictType
     */
    void cacheDeleteByDictType(final String dictType);

    /**
     * 刷新缓存
     */
    void flushCache();

}
