package com.hugai.core.dataPool.sign;

import com.hugai.core.dataPool.model.DataElement;

/**
 * @author WuHao
 * @since 2023/11/29 15:19
 */
public interface DataExecute {

    /**
     * 获取数据值
     *
     * @return
     */
    DataElement getElement();

    /**
     * 执行器
     */
    void execute();

}
