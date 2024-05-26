package com.hugai.common.enums.flow;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuHao
 * @since 2023/11/28 9:51
 */
@Getter
@AllArgsConstructor
public enum ChatSdkType implements FlowBaseEnum {

    openai("openai", "openai模型"),

    spark("spark", "讯飞星火平台"),

    baidu("baidu", "百度千帆大模型"),

    ollama("Ollama", "LLM平台"),
    ;

    private final String key;

    private final String value;
}
