package com.hugai.common.modules.entity.user.convert;

import com.hugai.common.modules.entity.user.dto.UserInfoDTO;
import com.hugai.common.modules.entity.user.vo.ClientRegisterBody;
import com.hugai.common.modules.entity.user.vo.UserInfoDetailVo;
import com.hugai.common.modules.entity.system.vo.auth.RegisterBody;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface UserInfoConvert extends BaseConvert<UserInfoModel, UserInfoDTO> {

    UserInfoConvert INSTANCE = Mappers.getMapper(UserInfoConvert.class);

    UserInfoDTO convertToRegister(RegisterBody param);

    UserInfoDetailVo convertDetail(UserInfoModel param);

    UserInfoModel convertClientRegisterToModel(ClientRegisterBody param);

}
