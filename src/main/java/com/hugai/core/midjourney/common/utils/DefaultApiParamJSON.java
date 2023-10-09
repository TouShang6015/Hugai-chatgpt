package com.hugai.core.midjourney.common.utils;

import java.util.Map;

/**
 * 默认api请求JSON参数
 *
 * @author WuHao
 * @since 2023/9/26 16:37
 */
public class DefaultApiParamJSON {

    private Map<String, String> apiDefaultParamMap;

    public Map<String, String> getApiDefaultParamMap() {
        return apiDefaultParamMap;
    }

    public void setApiDefaultParamMap(Map<String, String> apiDefaultParamMap) {
        this.apiDefaultParamMap = apiDefaultParamMap;
    }
}