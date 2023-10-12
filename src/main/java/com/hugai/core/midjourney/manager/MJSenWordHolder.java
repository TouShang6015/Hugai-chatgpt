package com.hugai.core.midjourney.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.org.bebas.utils.acword.Emit;
import com.org.bebas.utils.acword.Emits;
import com.org.bebas.utils.acword.Trie;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 敏感词处理
 *
 * @author WuHao
 * @since 2023-07-28 14:17:54
 * <p>
 * 使用AC自动机实现敏感词处理
 * 核心代码源自Github：Leego Yih，找不到源仓库地址了
 * </p>
 */
@Slf4j
public class MJSenWordHolder {

    private Trie trie;

    public MJSenWordHolder() {
        try {
            List<String> lines = readLines();
            this.trie = new Trie((new HashSet<>(lines)));
            log.info("敏感词实例初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("敏感词实例始化失败 - {}", e.getMessage());
        }
    }

    private List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();
        try (
                InputStream inputStream = this.getClass().getResourceAsStream("/text/mj-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * 内容中是否存在敏感词
     *
     * @param input
     * @return
     */
    public boolean exists(String input, boolean ifLog) {
        Emits emits = this.trie.findAll(input);
        if (CollUtil.isNotEmpty(emits)) {
            log.warn("存在敏感词：{}", emits.parallelStream().map(Emit::getKeyword).collect(Collectors.joining(",")));
        }
        return emits.size() != 0;
    }

    public boolean exists(String input) {
        return exists(input, true);
    }

    /**
     * 内容中是否存在敏感词
     *
     * @param data
     * @return
     */
    public boolean exists(Object data) {
        if (Objects.isNull(data)) {
            return false;
        }
        return this.exists(JSON.toJSONString(data));
    }

    /**
     * 将敏感词替换为新的字符
     *
     * @param input
     * @param replaceValue
     * @return
     */
    public String replace(String input, String replaceValue) {
        Emits emits = this.trie.findAllIgnoreCase(input);
        if (emits.size() == 0)
            return input;
        if (StrUtil.isEmpty(replaceValue))
            replaceValue = "*";
        return emits.replaceWith(replaceValue);
    }

    /**
     * 将敏感词替换为 *
     *
     * @param input
     * @return
     */
    public String replaceCommon(String input) {
        return this.replace(input, "*");
    }

}
