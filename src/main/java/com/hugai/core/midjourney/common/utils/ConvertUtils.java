package com.hugai.core.midjourney.common.utils;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ConvertUtils {
    /**
     * content正则匹配prompt和进度.
     */
    public static final String CONTENT_REGEX = ".*?\\*\\*(.*?)\\*\\*.+<@\\d+> \\((.*?)\\)";

    public static ContentParseData parseContent(String content) {
        return parseContent(content, CONTENT_REGEX);
    }

    public static ContentParseData parseContent(String content, String regex) {
        if (CharSequenceUtil.isBlank(content)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (!matcher.find()) {
            return null;
        }
        ContentParseData parseData = new ContentParseData();
        parseData.setPrompt(matcher.group(1));
        parseData.setStatus(matcher.group(2));
        return parseData;
    }

}
