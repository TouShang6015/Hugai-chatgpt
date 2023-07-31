package com.hugai.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.modules.system.entity.dto.SysUserDTO;
import com.hugai.modules.system.entity.model.SysUserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息表 持久层接口
 *
 * @author WuHao
 * @date 2022-05-24 23:43:38
 */
public interface SysUserMapper extends BaseMapper<SysUserModel> {

    IPage<SysUserDTO> selectUnallocatedList(IPage<SysUserDTO> page, @Param("param") SysUserDTO param);

    IPage<SysUserDTO> selectAllocatedList(IPage<SysUserDTO> page, @Param("param") SysUserDTO param);

    List<SysUserDTO> selectListByParam(SysUserModel param);
}
