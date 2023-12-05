package com.hugai.core.dataPool.model;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/11/29 15:17
 */
@Data
public class DataElement {

    /**
     * 值
     */
    private String value;
    /**
     * 权重
     */
    private Integer weightValue;

    private Object originalElement;

}
