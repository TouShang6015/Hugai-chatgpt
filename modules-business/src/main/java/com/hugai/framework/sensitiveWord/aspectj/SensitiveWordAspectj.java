package com.hugai.framework.sensitiveWord.aspectj;

import com.hugai.framework.sensitiveWord.annotation.SensitiveContentFilter;
import com.hugai.framework.sensitiveWord.strategy.SenStrategyContext;
import com.hugai.framework.sensitiveWord.strategy.SensitiveWordStrategy;
import com.org.bebas.mapper.utils.ReflectUtil;
import com.org.bebas.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * todo 待测试
 *
 * @author WuHao
 * @since 2023/7/28 14:54
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class SensitiveWordAspectj {

    @Around(value = "@annotation(sensitiveContentFilter)")
    public Object doAround(ProceedingJoinPoint pjp, SensitiveContentFilter sensitiveContentFilter) throws Throwable {
        this.doBefore(pjp, sensitiveContentFilter);
        Object result = pjp.proceed();
        return this.doAfter(result, sensitiveContentFilter);
    }

    /**
     * 执行前的操作
     */
    public void doBefore(ProceedingJoinPoint pjp, SensitiveContentFilter sensitiveContentFilter) {
        SensitiveWordStrategy strategyService = SenStrategyContext.getStrategyService(sensitiveContentFilter.type());
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof String) {
                args[i] = strategyService.defaultReplaceValue((String) arg, sensitiveContentFilter.replaceVal());
            } else {
                Object value = ReflectUtil.getFieldValue(arg, sensitiveContentFilter.attrName());
                args[i] = strategyService.defaultReplaceValue((String) value, sensitiveContentFilter.replaceVal());
            }
        }
    }

    /**
     * 执行后的操作
     */
    public Object doAfter(Object result, SensitiveContentFilter sensitiveContentFilter) {
        SensitiveWordStrategy strategyService = SenStrategyContext.getStrategyService(sensitiveContentFilter.resultType());
        Object finalResult = result;
        if (result instanceof Result) {
            Object resultData = ((Result) result).getData();
            if (resultData instanceof String) {
                String data = strategyService.defaultReplaceValue((String) resultData, sensitiveContentFilter.replaceVal());
                ((Result) finalResult).setData(data);
            }
        } else {
            if (result instanceof String) {
                finalResult = strategyService.defaultReplaceValue((String) result, sensitiveContentFilter.replaceVal());
            }
        }
        return finalResult;
    }

}
