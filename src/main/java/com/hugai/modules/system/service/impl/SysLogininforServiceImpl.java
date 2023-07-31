package com.hugai.modules.system.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.ChannelEnum;
import com.hugai.framework.asyncMessage.MessageService;
import com.hugai.framework.asyncMessage.annotation.MessageListener;
import com.hugai.modules.system.mapper.SysLogininforMapper;
import com.hugai.modules.system.service.ISysLogininforService;
import com.hugai.modules.system.entity.model.SysLogininforModel;
import com.org.bebas.core.json.JSONObjectBuilder;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.ip.AddressUtils;
import com.org.bebas.utils.ip.IpUtils;
import com.org.bebas.utils.logs.LogUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * 系统访问记录 业务实现类
 *
 * @author WuHao
 * @date 2022-05-25 08:51:34
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininforModel> implements ISysLogininforService {

    @Resource
    private MessageService messageService;

    @Override
    public void insertLoginLog(String loginType, String username, String status, String message) {
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        JSONObject jsonObject = JSONObjectBuilder.builder()
                .put("username", username)
                .put("status", status)
                .put("message", message)
                .put("loginType", loginType)
                .put("ip", ip)
                .put("os", userAgent.getOperatingSystem().getName())
                .put("browser", userAgent.getBrowser())
                .build();
        messageService.send(ChannelEnum.log_login, jsonObject.toJSONString());
    }

    @MessageListener(ChannelEnum.log_login)
    public void loginLogInsert(byte[] data) {
        JSONObject jsonObject = JSONObject.parseObject(new String(data, Charset.defaultCharset()));
        String username = jsonObject.get("username").toString();
        String status = jsonObject.get("status").toString();
        String message = jsonObject.get("message").toString();
        String ip = jsonObject.get("ip").toString();
        String os = jsonObject.get("os").toString();
        String browser = jsonObject.get("browser").toString();
        String loginType = jsonObject.get("loginType").toString();

        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(LogUtil.getBlock(ip));
        s.append(address);
        s.append(LogUtil.getBlock(username));
        s.append(LogUtil.getBlock(status));
        s.append(LogUtil.getBlock(message));
        // 打印信息到日志
        log.info(s.toString());
        // 封装对象
        SysLogininforModel logininfor = new SysLogininforModel();
        logininfor.setUserName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setMsg(message);
        logininfor.setLoginTime(new Date());
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setLoginType(loginType);
        // 日志状态
        if (StringUtils.equalsAny(status, LOGIN_SUCCESS, LOGOUT, REGISTER)) {
            logininfor.setStatus(Integer.valueOf(Constants.Status.NORMAL));
        } else if (LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(Integer.valueOf(Constants.Status.NO_NORMAL));
        }
        super.save(logininfor);
    }

    @Override
    public void clean() {
        baseMapper.clean();
    }
}
