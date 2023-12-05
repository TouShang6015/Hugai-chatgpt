package com.hugai.core.midjourney.valid;

import cn.hutool.core.util.StrUtil;
import com.hugai.core.midjourney.manager.MJSenWordHolder;
import com.hugai.core.midjourney.valid.annotation.FilterSenWord;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class FilterSenWordValidator implements ConstraintValidator<FilterSenWord, String> {

    @Override
    public void initialize(FilterSenWord constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        log.info("mj敏感词过滤 - Value值:{}", value);
        return !SpringUtils.getBean(MJSenWordHolder.class).exists(value);
    }
}
