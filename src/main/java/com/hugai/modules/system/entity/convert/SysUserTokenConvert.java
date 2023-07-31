package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.SysUserTokenDTO;
import com.hugai.modules.system.entity.model.SysUserTokenModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 登录人token信息 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysUserTokenConvert extends BaseConvert<SysUserTokenModel, SysUserTokenDTO> {

    SysUserTokenConvert INSTANCE = Mappers.getMapper(SysUserTokenConvert.class);

}
