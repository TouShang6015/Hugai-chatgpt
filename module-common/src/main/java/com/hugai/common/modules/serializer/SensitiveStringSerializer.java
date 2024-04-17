package com.hugai.common.modules.serializer;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hugai.common.constants.SecurityConstant;
import com.org.bebas.core.security.SecurityBaseUtil;

import java.io.IOException;

/**
 * 字符串加敏
 *
 * @author WuHao
 * @since 2023/12/4 9:59
 */
public class SensitiveStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            if (SecurityConstant.SYSTEM_ID.equals(SecurityBaseUtil.getUserId())) {
                gen.writeString(value);
                return;
            }
        } catch (Exception ignored) {
        }
        if (StrUtil.isNotEmpty(value)) {
            int length = value.length();
            int maskLength = length / 2;
            int start = (length - maskLength) / 2;
            int end = start + maskLength;

            String maskedValue = value.substring(0, start) + StrUtil.repeat("*", maskLength) + value.substring(end);
            gen.writeString(maskedValue);
        }
    }


}
