package com.hugai.modules.user.service.auth.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.constants.MessageCode;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.common.modules.entity.user.convert.UserInfoConvert;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.common.modules.entity.user.valid.GroupRegisterByEmail;
import com.hugai.common.modules.entity.user.vo.ClientRegisterBody;
import com.hugai.common.utils.NameRandomUtil;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.mail.enums.SmsCodeEnum;
import com.hugai.core.mail.enums.SmsTypeEnum;
import com.hugai.core.mail.service.SmsSendService;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.user.service.UserInfoService;
import com.hugai.modules.user.service.auth.UserRegisterService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.ip.AddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.hugai.common.constants.MessageCode.System.SYSTEM_NOT_OPEN_REGISTER;


/**
 * @author WuHao
 * @date 2022/5/29 16:26
 */
@RequiredArgsConstructor
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserInfoService userInfoService;

    private final BaseResourceWebApi baseResourceWebApi;

    private final SmsSendService smsSendService;

    /**
     * 游客注册
     *
     * @param ipAddress
     */
    @Override
    public UserInfoModel doTouristRegister(String ipAddress) {
        UserInfoModel userInfoModel = UserInfoModel.builder()
                .ipaddress(ipAddress)
                .ipLocation(AddressUtils.getRealAddressByIP(ipAddress))
                .status(Constants.Disable.NORMAL)
                .ifTourist(Constants.BOOLEAN.TRUE)
                .imgUrl(baseResourceWebApi.getResourceMain().getDefaultUserTouristImgUrl())
                .build();
        userInfoService.save(userInfoModel);
        return userInfoModel;
    }

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
    @Transactional
    @Override
    public UserInfoModel doRegisterByEmail(ClientRegisterBody param) {
        ValidatorUtil.validateEntity(param, GroupRegisterByEmail.class);

        Assert.isFalse(!param.getPassword().equals(param.getPassword2()), () -> new BusinessException("两次密码输入不一致"));

        // 验证推广码
        String promoCode = param.getPromoCode();
        UserInfoModel inviterUserInfo = null;
        if (StrUtil.isNotEmpty(promoCode)) {
            inviterUserInfo = userInfoService.lambdaQuery().eq(UserInfoModel::getPromoCode, promoCode).one();
            Assert.notNull(inviterUserInfo, () -> new BusinessException("推广码无效"));
        }

        // 验证码校验
        SmsCodeEnum smsCodeResult = smsSendService.verifyCode(SmsTypeEnum.REGISTER, param.getEmail(), param.getCode());
        if (!smsCodeResult.success()) {
            throw new BusinessException(smsCodeResult.getMessage());
        }

        ResourceMainVO resourceMainVO = baseResourceWebApi.getResourceMain();
        if (!resourceMainVO.getRegisterOpen())
            throw new BusinessException(MessageUtils.message(SYSTEM_NOT_OPEN_REGISTER));
        // 检验是否存在当前用户
        if (userInfoService.lambdaQuery().eq(UserInfoModel::getEmail, param.getEmail()).count() > 0) {
            throw new BusinessException(MessageUtils.message(MessageCode.User.USER_UNIQUE));
        }
        // 设置用户参数
        UserInfoModel insertParam = UserInfoConvert.INSTANCE.convertClientRegisterToModel(param);
        OR.run(insertParam.getPassword(), StrUtil::isNotEmpty, password -> insertParam.setPassword(SecurityContextUtil.encryptPassword(password)));
        insertParam.setIfTourist(Constants.BOOLEAN.FALSE);
        insertParam.setPromoCode(this.generateUniquePromoCode(true));
        insertParam.setUserName(RandomUtil.randomString(Constants.BASE_RANDOM_CHAR, 16));
        insertParam.setNickName(NameRandomUtil.generateRandomName(2, 6));
        insertParam.setInviteePromoCode(promoCode);
        insertParam.setPromoUserId(Objects.isNull(inviterUserInfo) ? null : inviterUserInfo.getId());
        insertParam.setImgUrl(resourceMainVO.getDefaultUserImgUrl());
        if (!userInfoService.save(insertParam)) {
            throw new BusinessException(MessageUtils.message(MessageCode.User.USER_REGISTER_FAIL));
        }
        smsSendService.changeEntityStatus(SmsTypeEnum.REGISTER, insertParam.getEmail(), smsParam -> smsParam.setStatus(true));
        return insertParam;
    }

    /**
     * 生成唯一推广码
     *
     * @return
     */
    @Override
    public String generateUniquePromoCode(boolean verify, int length) {
        String randomString = RandomUtil.randomString(Constants.BASE_RANDOM_CHAR, length);
        if (verify) {
            if (userInfoService.lambdaQuery().eq(UserInfoModel::getPromoCode, randomString).count() > 0) {
                return generateUniquePromoCode(true);
            }
        }
        return randomString;
    }
}
