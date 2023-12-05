package com.hugai.framework.file.context;

import cn.hutool.core.lang.Assert;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.framework.file.constants.FileStrategyEnum;
import com.hugai.framework.file.service.FileService;
import com.hugai.common.modules.entity.system.model.SysFileConfigModel;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/5/29 15:58
 */
@Component
public class FileServiceContext {

    @Resource
    private SysFileConfigService sysFileConfigService;
    @Resource
    private List<FileService> fileServiceList;
    @Resource
    private BaseResourceWebApi baseResourceWebApi;


    /**
     * 获取文件上传策略服务
     *
     * @return
     */
    public FileService getFileService() {
        ResourceMainVO resourceMainVO = baseResourceWebApi.getResourceMain();
        return this.getFileService(resourceMainVO.getFileSaveStrategy());
    }

    /**
     * 获取文件上传策略服务
     *
     * @param strategySign
     * @return
     */
    public FileService getFileService(String strategySign) {
        return this.getFileServiceList().stream().filter(item -> item.strategy().name().equals(strategySign)).findFirst().orElseThrow(() -> new BusinessException("没有找到文件上传服务"));
    }

    public List<FileService> getFileServiceList() {
        Assert.notEmpty(fileServiceList, () -> new BusinessException("没有找到文件上传服务"));
        List<String> uniqueKeys = fileServiceList.stream().map(FileService::strategy).map(FileStrategyEnum::name).distinct().collect(Collectors.toList());

        List<SysFileConfigModel> fileConfigList = sysFileConfigService.lambdaQuery().in(SysFileConfigModel::getUniqueKey, uniqueKeys).list();
        fileServiceList.forEach(item ->
                item.setFileConfig(
                        fileConfigList.stream()
                                .filter(config -> config.getUniqueKey().equals(item.strategy().name()))
                                .findFirst()
                                .orElse(null)
                )
        );
        return fileServiceList;
    }

}
