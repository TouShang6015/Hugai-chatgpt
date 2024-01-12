package com.hugai.chatsdk.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingType;
import com.org.bebas.core.function.OR;

import java.util.List;
import java.util.Objects;

/**
 * 估计token工具类
 *
 * @author WuHao
 * @since 2023/6/1 14:32
 */
public class TokenCalculateUtil {

    private static Encoding encoding;

    static {
        encoding = Encodings.newDefaultEncodingRegistry().getEncoding(EncodingType.CL100K_BASE);
    }

    /**
     * 根据内容获取消耗的token数
     *
     * @param content
     * @return
     */
    public static int getTokenNumOfContent(String content) {
        if (StrUtil.isEmpty(content))
            return 0;
        return encoding.encode(content).size();
    }

    /**
     * 根据内容获取消耗的token数
     *
     * @param t
     * @return
     */
    public static <T> int getTokenNumOfContents(T t) {
        return getTokenNumOfContents(CollUtil.newArrayList(t));
    }

    /**
     * 多轮对话
     *
     * @param tList
     * @return
     */
    public static <T> int getTokenNumOfContents(List<T> tList) {
        if (CollUtil.isEmpty(tList))
            return 0;
        StringBuilder strBuilder = new StringBuilder();
        OR.run(JSONArray.parseArray(JSONArray.toJSONString(tList), JSONObject.class), CollUtil::isNotEmpty, jsonArrays -> {
            jsonArrays.forEach(item -> {
                if (Objects.nonNull(item)) {
                    strBuilder.append("{")
                            .append("message:").append(item.get("content"))
                            .append("}");
                }
            });
        });
        return encoding.encode(strBuilder.toString()).size();
    }

}
