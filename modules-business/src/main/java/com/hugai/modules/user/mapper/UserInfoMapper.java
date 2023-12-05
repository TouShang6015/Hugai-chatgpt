package com.hugai.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author WuHao
 * @since 2023/6/5 10:22
 */
public interface UserInfoMapper extends BaseMapper<UserInfoModel> {

    @Select("select * from tb_user_info where if_tourist = '1' and ipaddress = #{ipaddress}")
    UserInfoModel selectByIpaddress(@Param("ipaddress") String ipaddress);

}
