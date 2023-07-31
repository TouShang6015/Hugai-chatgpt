package com.hugai.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * aop全局事务
 *
 * @author WuHao
 * @date 2022/5/29 21:02
 */
@Aspect
@Order(-2)
@Configuration
@Slf4j
public class TransactionConfig {

    @Value("${project.tx.timeOut}")
    private Integer timeOut;

    @Value("${project.tx.pointcutExpression}")
    private String aopPointcutExpression;

    @Autowired
    private TransactionManager transactionManager;

    @Bean("txAdvice")
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        Map<String, TransactionAttribute> map = this.handleMap();
        source.setNameMap(map);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean("advisor")
    public Advisor txAdviceAdvisor() {
        log.info("- 全局事务开启");
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(aopPointcutExpression);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

    /**
     * 回滚事务配置
     *
     * @return
     */
    private Map<String, TransactionAttribute> handleMap() {
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(timeOut);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("edit*", requiredTx);
        txMap.put("alert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("remove*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("do*", requiredTx);
        txMap.put("batch*", requiredTx);
        return txMap;
    }

}
