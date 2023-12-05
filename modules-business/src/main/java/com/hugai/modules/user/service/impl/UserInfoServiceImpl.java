package com.hugai.modules.user.service.impl;

import cn.hutool.core.lang.Assert;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.core.mail.entity.SmsBaseParam;
import com.hugai.core.mail.enums.SmsTypeEnum;
import com.hugai.core.mail.service.SmsSendService;
import com.hugai.modules.user.mapper.UserInfoMapper;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author WuHao
 * @since 2023/6/5 10:23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoModel> implements UserInfoService {

    @Resource
    private SmsSendService smsSendService;

    @Override
    public UserInfoModel selectUserByUserName(String username) {
        return super.lambdaQuery().eq(UserInfoModel::getUserName, username).one();
    }

    @Override
    public UserInfoModel selectUserByeEmail(String email) {
        return super.lambdaQuery().eq(UserInfoModel::getEmail, email).one();
    }

    @Override
    public UserInfoModel selectByIpaddress(String ipaddress) {
        return baseMapper.selectByIpaddress(ipaddress);
    }

    /**
     * 注册发送短信验证码
     *
     * @param email
     */
    @Override
    public void registerSendMail(String email) {
        Assert.notEmpty(email);
        Assert.isFalse(super.lambdaQuery().eq(UserInfoModel::getEmail, email).count() > 0, () -> new BusinessException("该邮箱已被注册，请更换其他邮箱号码"));
        SmsBaseParam smsParam = smsSendService.getEntity(SmsTypeEnum.REGISTER, email);
        if (smsParam != null) {
            if (!smsParam.getStatus()) {
                if (new Date().getTime() < DateUtils.addSeconds(smsParam.getCreateTime(), 60).getTime()) {
                    throw new BusinessException("验证码发送频繁，请稍后重试");
                }
            }
        }
        smsSendService.sendSmsCode(SmsTypeEnum.REGISTER, email, "欢迎加入HugAi，这是您的验证码%s请妥善保管，有效期为15分钟。");
    }

}
