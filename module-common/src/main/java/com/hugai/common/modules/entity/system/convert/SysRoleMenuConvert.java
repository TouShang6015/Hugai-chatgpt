package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysRoleMenuDTO;
import com.hugai.common.modules.entity.system.model.SysRoleMenuModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色和菜单关联表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysRoleMenuConvert extends BaseConvert<SysRoleMenuModel, SysRoleMenuDTO> {

    SysRoleMenuConvert INSTANCE = Mappers.getMapper(SysRoleMenuConvert.class);

}
