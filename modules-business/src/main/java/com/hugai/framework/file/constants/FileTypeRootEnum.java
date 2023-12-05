package com.hugai.framework.file.constants;

import com.hugai.framework.file.FileUtil;
import com.org.bebas.core.function.OR;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文件类型根目录枚举
 *
 * @author WuHao
 * @since 2023/5/29 16:21
 */
@Getter
@AllArgsConstructor
public enum FileTypeRootEnum {

    image(FileTypeConstants.IMAGE_EXTENSION),

    word(FileTypeConstants.DOCUMENT_EXTENSION),

    md(new String[]{"md"}),

    txt(new String[]{"txt"}),

    common(FileTypeConstants.ALL_EXTENSION),

    ;

    private final String[] suffixArray;

    /**
     * 根据后缀获取文件类型根目录
     *
     * @param suffix
     * @return
     */
    public static FileTypeRootEnum getTypeRootBySuffix(String suffix) {
        String finalSuffix = Optional.ofNullable(suffix).orElse("");
        return Arrays.stream(FileTypeRootEnum.values())
                .filter(item -> {
                    String[] suffixArray = item.getSuffixArray();
                    return Arrays.asList(suffixArray).contains(finalSuffix);
                })
                .findFirst()
                .orElse(FileTypeRootEnum.common);
    }

}
