package com.hugai.core.security.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户线程变量
 * <p>手动调用，可在子线程中获取主线程的threadLocal值</p>
 * <p>* 记得最后调用remove()防止内存泄漏</p>
 *
 * @author WuHao
 * @since 2023/8/22 13:45
 */
public class UserThreadLocal {

    private static final TransmittableThreadLocal<Long> threadLocal = new TransmittableThreadLocal<>();

    public static Long getUserId() {
        return threadLocal.get();
    }

    public static void set(Long userId) {
        threadLocal.set(userId);
    }

    public static void remove() {
        threadLocal.remove();
    }

}
