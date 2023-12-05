package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysUserRoleDTO;
import com.hugai.common.modules.entity.system.model.SysUserRoleModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户和角色关联表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysUserRoleConvert extends BaseConvert<SysUserRoleModel, SysUserRoleDTO> {

    SysUserRoleConvert INSTANCE = Mappers.getMapper(SysUserRoleConvert.class);

}
