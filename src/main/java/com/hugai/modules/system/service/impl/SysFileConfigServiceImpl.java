package com.hugai.modules.system.service.impl;

import com.hugai.modules.system.entity.model.SysFileConfigModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.mapper.SysFileConfigMapper;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 文件上传配置 业务实现类
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Service
public class SysFileConfigServiceImpl extends ServiceImpl<SysFileConfigMapper, SysFileConfigModel> implements SysFileConfigService {

    @Resource
    private IBaseResourceConfigService resourceConfigService;

    /**
     * 获取当前文件配置路径
     *
     * @return
     */
    @Override
    public String getFileConfigPath() {
        ResourceMainVO resourceMain = resourceConfigService.getResourceMain();
        String fileSaveStrategy = resourceMain.getFileSaveStrategy();
        SysFileConfigModel model = OptionalUtil.ofNullList(super.list()).stream().filter(item -> item.getUniqueKey().equals(fileSaveStrategy)).findFirst().orElse(null);
        if (Objects.isNull(model)) return null;
        return model.getSavePath();
    }
}
