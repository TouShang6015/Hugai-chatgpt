package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.SysRoleDTO;
import com.hugai.modules.system.entity.model.SysRoleModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色信息表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysRoleConvert extends BaseConvert<SysRoleModel, SysRoleDTO> {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

}
