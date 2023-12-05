package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysMenuDTO;
import com.hugai.common.modules.entity.system.model.SysMenuModel;
import com.hugai.common.modules.entity.system.vo.sysMenu.SysMenuTreeVo;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜单权限表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysMenuConvert extends BaseConvert<SysMenuModel, SysMenuDTO> {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    @Mappings({
            @Mapping(target = "label", source = "menuName"),
    })
    SysMenuTreeVo convertTree(SysMenuModel param);

    List<SysMenuTreeVo> convertTree(List<SysMenuModel> param);

    @Mappings({
            @Mapping(target = "label", source = "menuName"),
    })
    SysMenuTreeVo dtoConvertTree(SysMenuDTO param);

    List<SysMenuTreeVo> dtoConvertTree(List<SysMenuDTO> param);


}
