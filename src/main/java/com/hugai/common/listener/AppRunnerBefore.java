package com.hugai.common.listener;

import com.hugai.common.listener.impl.InitCache;
import com.org.bebas.utils.logs.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author WuHao
 * @date 2022/5/12 10:30
 */
@Component
@Slf4j
public class AppRunnerBefore implements InitializingBean {

    @Resource
    private List<InitCache> cacheList;

    @Override
    public void afterPropertiesSet() throws Exception {
        LogUtil.consoleInfo(log, "- 初始化缓存数据", () -> {
            Optional.ofNullable(cacheList).ifPresent(l -> l.forEach(InitCache::runInitCache));
        });
    }
}
