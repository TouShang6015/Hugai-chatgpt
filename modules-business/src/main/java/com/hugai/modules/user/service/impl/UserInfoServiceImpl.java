package com.hugai.modules.user.service.impl;

import cn.hutool.core.lang.Assert;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.common.support.sms.SmsServiceContext;
import com.hugai.common.support.sms.entity.SmsBaseParam;
import com.hugai.common.support.sms.enums.SmsStrategy;
import com.hugai.common.support.sms.enums.SmsTypeEnum;
import com.hugai.common.support.sms.service.SmsSendService;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
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
    private SmsServiceContext smsServiceContext;
    @Resource
    private BaseResourceWebApi resourceWebApi;

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

        SmsTypeEnum smsType = SmsTypeEnum.REGISTER;

        SmsSendService smsSendService = smsServiceContext.getService(SmsStrategy.mail.name(), () -> new BusinessException("没有找到短信策略"));
        SmsBaseParam smsParam = smsSendService.getEntity(smsType, email);

        if (smsParam != null) {
            if (!smsParam.getStatus()) {
                if (new Date().getTime() < DateUtils.addSeconds(smsParam.getCreateTime(), 60).getTime()) {
                    throw new BusinessException("验证码发送频繁，请稍后重试");
                }
            }
        }

        smsSendService.sendSmsCode(smsType, email, null);
    }

}
