package com.hugai.core.openai.utils;

import cn.hutool.core.collection.CollUtil;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatMessage;

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
        return encoding.encode(content).size();
    }

    /**
     * 根据内容获取消耗的token数
     *
     * @param chatMessage
     * @return
     */
    public static int getTokenNumOfContents(ChatMessage chatMessage) {
        return getTokenNumOfContents(CollUtil.newArrayList(chatMessage));
    }

    /**
     * 多轮对话
     *
     * @param chatMessageList
     * @return
     */
    public static int getTokenNumOfContents(List<ChatMessage> chatMessageList) {
        StringBuilder strBuilder = new StringBuilder();
        chatMessageList.forEach(item -> {
            if (Objects.nonNull(item)) {
                strBuilder.append("{")
                        .append("role:").append(item.getRole())
                        .append("message:").append(item.getContent())
                        .append("}");
            }
        });
        return encoding.encode(strBuilder.toString()).size();
    }

}
