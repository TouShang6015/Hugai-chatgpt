package com.hugai.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.org.bebas.utils.logs.LogUtil;
import io.vavr.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WuHao
 * @date 2022/5/17 9:12
 */
@Configuration
@Slf4j
@MapperScan(basePackages = {"com.hugai.modules.**.mapper"})
public class MybatisPlusConfig {

    /**
     * mybatis-plus 插件配置
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        LogUtil.consoleInfo(log, "- mybatis-plus 插件配置"
                , Tuple.of("- 分页插件配置", () -> interceptor.addInnerInterceptor(new PaginationInnerInterceptor()))
                , Tuple.of("- 乐观锁配置完成", () -> interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()))
        );
        return interceptor;
    }

    /**
     * 下划线转驼峰
     *
     * @return
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }
}
