package com.hugai.modules.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.hugai.common.enums.UserTypeEnum;
import com.hugai.core.mail.entity.SmsBaseParam;
import com.hugai.core.mail.enums.SmsTypeEnum;
import com.hugai.core.mail.service.SmsSendService;
import com.hugai.core.openai.entity.response.UserAccountResponse;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.model.account.Usage;
import com.hugai.core.openai.model.account.UserGrants;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.mapper.UserInfoMapper;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.DateUtils;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/6/5 10:23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoModel> implements UserInfoService {

    @Resource
    private IOpenaiKeysService openaiKeysService;
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
     * 获取账户api信息
     *
     * @return
     */
    @Override
    public List<UserAccountResponse> getUserGrants() {
        UserTypeEnum userType = SecurityContextUtil.getUserType();

        List<String> keys = null;
        if (UserTypeEnum.SYS.equals(userType)) {
            List<OpenaiKeysModel> keyModels = openaiKeysService.getCommonKey();
            if (CollUtil.isEmpty(keyModels))
                return null;
            keys = keyModels.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
        } else if (UserTypeEnum.USER.equals(userType)) {
            Long userId = SecurityContextUtil.getUserId();
            List<OpenaiKeysModel> keyModels = openaiKeysService.getByUser(userId);
            if (CollUtil.isEmpty(keyModels))
                return null;
            keys = keyModels.stream().map(OpenaiKeysModel::getApiKey).distinct().collect(Collectors.toList());
        }
        if (CollUtil.isEmpty(keys))
            return null;
        return keys.parallelStream().map(key -> {
            try {
                OpenAiService openAiService = AiServiceFactory.createService(key);
                UserAccountResponse userAccountInfo = openAiService.getUserAccountInfo();

                UserGrants userGrants = Optional.ofNullable(userAccountInfo.getUserGrants()).orElseGet(UserGrants::new);
                Usage usage = Optional.ofNullable(userAccountInfo.getUsage()).orElseGet(Usage::new);

                Double total = userGrants.getHardLimitUsd();
                double totalUsage = NumberUtil.div(
                        Optional.ofNullable(usage.getTotalUsage()).orElse(0d), new Double(100d), 4, RoundingMode.HALF_EVEN
                );

                OR.run(usage.getDailyCosts(), CollUtil::isNotEmpty, dailyCosts -> {
                    List<UserAccountResponse.UsableCondition> usableConditionList = dailyCosts.stream().map(item -> {
                        UserAccountResponse.UsableCondition usableCondition = new UserAccountResponse.UsableCondition();
                        String time = DateUtil.format(DateUtil.date(item.getTimestamp() * 1000), DatePattern.NORM_DATE_PATTERN);
                        Double totalCost = OptionalUtil.ofNullList(item.getLineItems()).stream().map(Usage.DailyCosts.LineItems::getCost)
                                .filter(Objects::nonNull)
                                .reduce(Double::sum)
                                .orElse(0d);
                        usableCondition.setTime(time);
                        usableCondition.setTotalCost(NumberUtil.div(totalCost, new Double(100d), 8, RoundingMode.HALF_EVEN));
                        return usableCondition;
                    }).collect(Collectors.toList());
                    userAccountInfo.setUsableConditionList(usableConditionList);
                });

                userAccountInfo.setApiKey(key);
                userAccountInfo.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                userAccountInfo.setTotalUsage(BigDecimal.valueOf(totalUsage).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                userAccountInfo.setTotalBalance(BigDecimal.valueOf(total - totalUsage).setScale(2, RoundingMode.HALF_EVEN).doubleValue());

                userAccountInfo.setUsage(null);
                userAccountInfo.setUserGrants(null);
                return userAccountInfo;
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 注册发送短信验证码
     *
     * @param email
     */
    @Override
    public void registerSendMail(String email) {
        Assert.notEmpty(email);
        Assert.isFalse(super.lambdaQuery().eq(UserInfoModel::getEmail,email).count() > 0,() -> new BusinessException("该邮箱已被注册，请更换其他邮箱号码"));
        SmsBaseParam smsParam = smsSendService.getEntity(SmsTypeEnum.REGISTER, email);
        if (smsParam != null){
            if (!smsParam.getStatus()){
                if (new Date().getTime() < DateUtils.addSeconds(smsParam.getCreateTime(), 60).getTime()){
                    throw new BusinessException("验证码发送频繁，请稍后重试");
                }
            }
        }
        smsSendService.sendSmsCode(SmsTypeEnum.REGISTER,email,"欢迎加入HugAi，这是您的验证码%s请妥善保管，有效期为15分钟。");
    }

}
