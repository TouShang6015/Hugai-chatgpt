package com.hugai.modules.system.service;

import com.hugai.modules.system.entity.model.SysFileConfigModel;
import com.org.bebas.mapper.service.IService;
import com.org.bebas.utils.OptionalUtil;

import java.util.List;

/**
 * 文件上传配置 业务接口
 *
 * @author WuHao
 * @date 2023-05-29
 */
public interface SysFileConfigService extends IService<SysFileConfigModel> {

    /**
     * 获取当前文件配置路径
     *
     * @return
     */
    String getFileConfigPath();

    /**
     * 获取所有配置
     *
     * @return
     */
    List<SysFileConfigModel> getAll();

    default SysFileConfigModel getByUniqueKye(String uniqueKey) {
        List<SysFileConfigModel> all = OptionalUtil.ofNullList(this.getAll());
        return all.stream().filter(item -> item.getUniqueKey().equals(uniqueKey)).findFirst().orElse(null);
    }

}
