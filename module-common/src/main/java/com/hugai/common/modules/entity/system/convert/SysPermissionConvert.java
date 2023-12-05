package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysPermissionDTO;
import com.hugai.common.modules.entity.system.model.SysPermissionModel;
import com.hugai.common.modules.entity.system.vo.permission.RouteInfo;
import com.hugai.common.modules.entity.system.vo.permission.SysPermissionTreeVo;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 权限管理 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysPermissionConvert extends BaseConvert<SysPermissionModel, SysPermissionDTO> {

    SysPermissionConvert INSTANCE = Mappers.getMapper(SysPermissionConvert.class);

    /**
     * 转换routeInfo
     *
     * @param modelList
     * @return
     */
    List<RouteInfo> convertToRouteInfo(List<SysPermissionModel> modelList);

    @Mappings({
            @Mapping(target = "label", source = "title"),
    })
    SysPermissionTreeVo convertTree(SysPermissionModel param);

    List<SysPermissionTreeVo> convertTree(List<SysPermissionModel> param);

    @Mappings({
            @Mapping(target = "label", source = "title"),
    })
    SysPermissionTreeVo dtoConvertTree(SysPermissionDTO param);

    List<SysPermissionTreeVo> dtoConvertTree(List<SysPermissionDTO> param);


}
