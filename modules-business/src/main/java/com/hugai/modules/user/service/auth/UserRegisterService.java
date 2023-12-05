package com.hugai.modules.user.service.auth;

import com.hugai.common.modules.entity.user.vo.ClientRegisterBody;
import com.hugai.common.modules.entity.user.model.UserInfoModel;

/**
 * @author WuHao
 * @date 2022/5/29 16:26
 */
public interface UserRegisterService {

    /**
     * 游客注册
     *
     * @param ipAddress
     */
    UserInfoModel doTouristRegister(String ipAddress);

    /**
     * 邮箱注册
     * <p>
     * userName -- 必填
     * password -- 密码
     * </p>
     *
     * @param param
     * @return
     */
    UserInfoModel doRegisterByEmail(ClientRegisterBody param);

    /**
     * 生成唯一推广码
     *
     * @param verify 是否需要验证
     * @return
     */
    String generateUniquePromoCode(boolean verify, int length);

    default String generateUniquePromoCode(boolean verify) {
        return generateUniquePromoCode(verify, 8);
    }

}
