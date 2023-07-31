package com.hugai.modules.system.entity.dto;

import com.hugai.modules.system.entity.model.SysMenuModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单权限表 Dto
 *
 * @author WuHao
 * @company Wuhao
 * @date 2022-05-25 19:02:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuDTO extends SysMenuModel {

    private Long userId;

}
