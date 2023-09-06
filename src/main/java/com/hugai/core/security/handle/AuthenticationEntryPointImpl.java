package com.hugai.core.security.handle;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.result.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author wuhao
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        int code = HttpStatus.HTTP_UNAUTHORIZED;
        String msg = "系统认证失败，请先登录后在操作";
        ServletUtils.renderString(response, JSON.toJSONString(Result.fail(code, msg)));
    }
}
