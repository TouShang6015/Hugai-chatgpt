package com.hugai.chatsdk.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author WuHao
 * @since 2023/6/1 9:08
 */
public class ContentUtil {

    public final static String lp = "↖";
    public final static String rp = "↘";

    public static String convertNormal(String content) {
        if (StrUtil.isEmpty(content)) {
            return content;
        }
        content = content.replace(" ", String.format("%semsp%s", lp, rp));
        content = content.replace("\n", String.format("%sbr%s", lp, rp));
        return content;
    }

}
