package com.hugai.core.security.service;

import com.hugai.common.constants.SecurityConstant;
import com.hugai.config.properties.TokenConfig;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.modules.system.service.ISysUserTokenService;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.ip.AddressUtils;
import com.org.bebas.utils.ip.IpUtils;
import com.org.bebas.utils.uuid.IdUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author WuHao
 * @date 2022/5/22 22:41
 */
@Primary
@Component
public class TokenService {

    // 秒
    private static final long MILLIS_SECOND = 1000;
    // 分钟
    private static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    // 小时
    private static final long MILLIS_MINUTE_TEN = 24 * MILLIS_MINUTE;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ISysUserTokenService sysUserTokenService;


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserContextBean getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(SecurityConstant.LOGIN_USER_KEY);

                return (LoginUserContextBean) redisTemplate.opsForValue().get(getTokenKey(uuid));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUserContextBean loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            redisTemplate.delete(getTokenKey(token));
            sysUserTokenService.removeByTokenKey(token);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUserContextBean loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstant.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUserContextBean loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= TokenConfig.getFlushToken()) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUserContextBean 登录信息
     */
    public void refreshToken(LoginUserContextBean loginUserContextBean) {
        loginUserContextBean.setLoginTime(System.currentTimeMillis());
        loginUserContextBean.setExpireTime(loginUserContextBean.getLoginTime() + TokenConfig.getExpireTime());
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUserContextBean.getToken());
        redisTemplate.opsForValue().set(userKey, loginUserContextBean, TokenConfig.getExpireTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUserContextBean loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, TokenConfig.getSecret()).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(TokenConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(TokenConfig.getHeader());
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 根据uuid获取redis存放的token key
     *
     * @param tokenOrUuid
     * @return
     */
    public String getTokenKey(String tokenOrUuid) {
        return SecurityConstant.LOGIN_TOKEN_KEY + tokenOrUuid;
    }

}
