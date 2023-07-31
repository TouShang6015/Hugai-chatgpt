package com.hugai.framework.file.constants;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author wuhao
 * @date 2022/9/23 10:49
 */
@AllArgsConstructor
public enum FileHeaderImageEnum implements FlowBaseEnum {

    IMAGE_PNG("image/png", ".png"),
    IMAGE_JPG("image/jpg", ".jpg"),
    IMAGE_JPEG("image/jpeg", ".jpeg"),
    IMAGE_BMP("image/bmp", ".bmp"),
    IMAGE_GIF("image/gif", ".gif"),
    ;

    private final String key;

    private final String value;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

}
