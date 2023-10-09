package com.hugai.modules.system.service.impl;

import com.hugai.common.listener.impl.InitCache;
import com.hugai.modules.system.entity.model.SysFileConfigModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.mapper.SysFileConfigMapper;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.constants.RedisConstant;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传配置 业务实现类
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Service
public class SysFileConfigServiceImpl extends ServiceImpl<SysFileConfigMapper, SysFileConfigModel> implements SysFileConfigService, InitCache {

    private final String CACHE_KEY = ModelUtil.modelMainKey(SysFileConfigModel.class) + RedisConstant.Keyword.ALL_LIST;

    @Resource
    private IBaseResourceConfigService resourceConfigService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取当前文件配置路径
     *
     * @return
     */
    @Override
    public String getFileConfigPath() {
        ResourceMainVO resourceMain = resourceConfigService.getResourceMain();
        String fileSaveStrategy = resourceMain.getFileSaveStrategy();
        SysFileConfigModel model = this.getAll().stream().filter(item -> item.getUniqueKey().equals(fileSaveStrategy)).findFirst().orElse(null);
        if (Objects.isNull(model)) return null;
        return model.getSavePath();
    }

    /**
     * 获取所有配置
     *
     * @return
     */
    @Override
    public List<SysFileConfigModel> getAll() {
        return redisUtil.getCacheList(CACHE_KEY);
    }

    /**
     * 初始化缓存
     */
    @Override
    public void runInitCache() {
        List<SysFileConfigModel> list = OptionalUtil.ofNullList(super.list());
        redisUtil.setCacheList(CACHE_KEY, list);
    }

    @Override
    public boolean save(SysFileConfigModel entity) {
        if (super.save(entity)) {
            this.runInitCache();
        }
        return true;
    }

    @Override
    public boolean updateById(SysFileConfigModel entity) {
        if (super.updateById(entity)) {
            this.runInitCache();
        }
        return true;
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        if (super.removeByIds(list)) {
            this.runInitCache();
        }
        return true;
    }
}
