package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.SysRolePermissionDTO;
import com.hugai.modules.system.entity.model.SysRolePermissionModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色和权限关联表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysRolePermissionConvert extends BaseConvert<SysRolePermissionModel, SysRolePermissionDTO> {

    SysRolePermissionConvert INSTANCE = Mappers.getMapper(SysRolePermissionConvert.class);

}
