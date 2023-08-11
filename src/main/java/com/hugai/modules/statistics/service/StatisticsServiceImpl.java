package com.hugai.modules.statistics.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.Constants;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionInfoModel;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.statistics.entity.vo.DeskStatisticsDataVO;
import com.hugai.modules.statistics.entity.vo.UserSessionStatisticsDataVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.utils.DateUtils;
import com.org.bebas.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author WuHao
 * @since 2023/8/10 15:37
 */
@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final UserInfoService userInfoService;

    private final SessionInfoService sessionInfoService;

    private final SessionInfoDrawService sessionInfoDrawService;

    private final IBaseResourceConfigService resourceConfigService;

    private final IOpenaiKeysService openaiKeysService;

    /**
     * 桌面部分信息返回
     *
     * @return
     */
    @Override
    public DeskStatisticsDataVO getDeskCommonData() {
        DeskStatisticsDataVO result = new DeskStatisticsDataVO();

        Long touristCount = userInfoService.lambdaQuery().eq(UserInfoModel::getIfTourist, Constants.BOOLEAN.TRUE).count();
        Long userCount = userInfoService.lambdaQuery().eq(UserInfoModel::getIfTourist, Constants.BOOLEAN.FALSE).count();

        String webIssueTime = Optional.ofNullable(resourceConfigService.getResourceMain().getWebIssueTime()).orElse(DateUtils.parseDateToStr(DatePattern.NORM_DATE_PATTERN, new Date()));

        long runDay = DateUtil.betweenDay(DateUtils.dateTime(DatePattern.NORM_DATE_PATTERN, webIssueTime), new Date(), true);

        result.setTouristCount(Math.toIntExact(touristCount));
        result.setUserCount(Math.toIntExact(userCount));
        result.setRunDay(Math.toIntExact(runDay));
        return result;
    }

    /**
     * 获取用户会话统计信息
     *
     * @return
     */
    @Override
    public UserSessionStatisticsDataVO getUserSessionStatisticsData() {
        UserSessionStatisticsDataVO result = new UserSessionStatisticsDataVO();
        LoginUserContextBean loginUser = SecurityContextUtil.getLoginUser();
        Long userId = loginUser.getUserId();

        SessionInfoModel queryParam = new SessionInfoModel();
        queryParam.setUserId(userId);
        Integer tokenConsumer = (Integer) sessionInfoService.sum(SessionInfoModel::getAllConsumerToken, queryParam);
        Long sessionDrawCount = sessionInfoDrawService.lambdaQuery().eq(SessionInfoDrawModel::getUserId, userId).count();
        Long sessionCount = sessionInfoService.lambdaQuery().eq(SessionInfoModel::getUserId, userId).count();
        int tokenNum = OptionalUtil.ofNullList(openaiKeysService.getAbleKeys()).size();

        String showName = StrUtil.isNotEmpty(loginUser.getUsername()) ? loginUser.getUsername() : loginUser.getIpaddr();
        result.setShowName(showName);
        result.setSessionCount(Math.toIntExact(sessionCount));
        result.setSessionDrawCount(Math.toIntExact(sessionDrawCount));
        result.setTokenConsumer(tokenConsumer);
        result.setJoinTime(loginUser.getCreateTime());
        result.setTokenNum(tokenNum);
        return result;
    }
}
