package com.hugai.common.utils;

import com.org.bebas.core.function.SingleFunction;
import com.org.bebas.core.spring.SpringUtils;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author WuHao
 * @since 2023/11/18 16:26
 */
public class TransactionUtil {

    private static final TransactionTemplate transactionTemplate;

    static {
        transactionTemplate = SpringUtils.getBean(TransactionTemplate.class);
    }

    /**
     * 事务分隔执行
     *
     * @param function
     */
    public static void execute(SingleFunction function) {
        transactionTemplate.execute(status -> {
            try {
                function.run();
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return true;
        });
    }
}
