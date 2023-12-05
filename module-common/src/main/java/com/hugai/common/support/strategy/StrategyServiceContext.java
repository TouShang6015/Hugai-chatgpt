package com.hugai.common.support.strategy;

import cn.hutool.core.lang.Assert;
import com.org.bebas.exception.BusinessException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 策略接口中心
 * <p>* 仅适用Spring管理的策略接口</p>
 *
 * @author WuHao
 * @since 2023/11/30 14:05
 */
public abstract class StrategyServiceContext<Service> {

    protected List<Service> serviceList;

    public StrategyServiceContext(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    protected abstract Function<Service, String> serviceKey();

    /**
     * 根据key获取对应策略器
     *
     * @param key               指定唯一标识
     * @param exceptionSupplier 自定义异常信息
     * @return
     */
    public Service getService(String key, Supplier<BusinessException> exceptionSupplier) {
        for (Service service : serviceList) {
            String serviceTypeKey = this.serviceKey().apply(service);
            Assert.notEmpty(serviceTypeKey, exceptionSupplier);
            if (serviceTypeKey.toUpperCase(Locale.ROOT).equals(key.toUpperCase(Locale.ROOT))) {
                return service;
            }
        }
        if (Objects.nonNull(exceptionSupplier)){
            throw exceptionSupplier.get();
        }
        return null;
    }

}
