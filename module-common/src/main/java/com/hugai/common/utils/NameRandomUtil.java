package com.hugai.common.utils;

import java.util.Random;

/**
 * @author WuHao
 * @since 2023/10/26 10:05
 */
public class NameRandomUtil {

    public static String[] nameStrings = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "楮", "卫",
            "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许", "何", "吕", "施",
            "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜",
    };

    /**
     * 生成随机名称
     *
     * @param start
     * @param end
     * @return
     */
    public static String generateRandomName(int start, int end) {
        int targetStringLength = new Random().nextInt(end - start) + start;
        StringBuilder nickname = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomIndex = new Random().nextInt(nameStrings.length);
            nickname.append(nameStrings[randomIndex]);
        }
        return nickname.toString();
    }

}
