package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.model.BaseDictDataModel;
import com.org.bebas.core.label.LabelOption;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 字典数据表 业务接口
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
public interface IBaseDictDataService extends IService<BaseDictDataModel> {


    /**
     * 获取下拉
     *
     * @return
     */
    List<LabelOption<String, String>> optionSelect(String dictType);

    /**
     * 通过字典类型跟值获取对象
     *
     * @param dictType
     * @param dictValue
     * @return
     */
    BaseDictDataModel selectOneByTypeAndValue(String dictType, String dictValue);

    /**
     * 通过字典类型获取列表
     *
     * @param dictType
     * @return
     */
    List<BaseDictDataModel> selectListByDictType(String dictType);

    /**
     * 根据id获取单个结果
     *
     * @param id
     * @return
     */
    BaseDictDataModel selectOneById(Long id);

    /**
     * 修改
     *
     * @param param
     * @return
     */
    boolean updateByParam(BaseDictDataModel param);

    /**
     * 新增
     *
     * @param param
     * @return
     */
    boolean insertByParam(BaseDictDataModel param);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);

    /**
     * 刷新缓存
     */
    void flushCache(String dictType);

    /**
     * 刷新缓存
     */
    void flushCache();

}
