package com.hugai.modules.user.service.login.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.constants.MessageCode;
import com.hugai.core.mail.enums.SmsCodeEnum;
import com.hugai.core.mail.enums.SmsTypeEnum;
import com.hugai.core.mail.service.SmsSendService;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.system.entity.vo.auth.ClientRegisterBody;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.user.entity.convert.UserInfoConvert;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.hugai.modules.user.service.login.UserRegisterService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hugai.common.constants.MessageCode.System.SYSTEM_NOT_OPEN_REGISTER;


/**
 * @author WuHao
 * @date 2022/5/29 16:26
 */
@RequiredArgsConstructor
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserInfoService userInfoService;

    private final IBaseResourceConfigService baseResourceConfigService;

    private final SmsSendService smsSendService;

    /**
     * 注册接口
     * <p>
     * userName -- 必填
     * nickName -- 必填
     * password -- 密码
     * </p>
     *
     * @param param
     * @return
     */
    @Override
    public Result doRegister(ClientRegisterBody param) {
        ValidatorUtil.validateEntity(param);

        Assert.isFalse(!param.getPassword().equals(param.getPassword2()),() -> new BusinessException("两次密码输入不一致"));

        // 验证码校验
        SmsCodeEnum smsCodeResult = smsSendService.verifyCode(SmsTypeEnum.REGISTER, param.getEmail(), param.getCode());
        if (!smsCodeResult.success()) {
            throw new BusinessException(smsCodeResult.getMessage());
        }

        ResourceMainVO resourceMainVO = baseResourceConfigService.getResourceMain();
        if (!resourceMainVO.getRegisterOpen())
            throw new BusinessException(MessageUtils.message(SYSTEM_NOT_OPEN_REGISTER));
        // 检验是否存在当前用户
        if (userInfoService.lambdaQuery().eq(UserInfoModel::getUserName, param.getUserName()).count() > 0) {
            return Result.fail(MessageUtils.message(MessageCode.User.USER_UNIQUE));
        }
        if (userInfoService.lambdaQuery().eq(UserInfoModel::getEmail, param.getEmail()).count() > 0) {
            return Result.fail(MessageUtils.message(MessageCode.User.USER_UNIQUE));
        }
        // 设置用户参数
        UserInfoModel insertParam = UserInfoConvert.INSTANCE.convertClientRegisterToModel(param);
        OR.run(insertParam.getPassword(), StrUtil::isNotEmpty, password -> insertParam.setPassword(SecurityContextUtil.encryptPassword(password)));
        insertParam.setIfTourist(Constants.BOOLEAN.FALSE);
        if (userInfoService.save(insertParam)) {
            smsSendService.changeEntityStatus(SmsTypeEnum.REGISTER,insertParam.getEmail(),smsParam -> smsParam.setStatus(true));
            return Result.success(MessageUtils.message(MessageCode.User.USER_REGISTER_SUCCESS));
        }
        return Result.fail(MessageUtils.message(MessageCode.User.USER_REGISTER_FAIL));
    }
}
