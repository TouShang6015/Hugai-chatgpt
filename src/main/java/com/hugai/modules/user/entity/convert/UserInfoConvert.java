package com.hugai.modules.user.entity.convert;

import com.hugai.modules.system.entity.vo.auth.ClientRegisterBody;
import com.hugai.modules.system.entity.vo.auth.RegisterBody;
import com.hugai.modules.user.entity.dto.UserInfoDTO;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.entity.vo.UserInfoDetailVo;
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
