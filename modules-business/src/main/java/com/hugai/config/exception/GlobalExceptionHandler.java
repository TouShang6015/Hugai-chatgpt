package com.hugai.config.exception;

import com.alibaba.fastjson2.JSON;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.exception.CommonException;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理器
 *
 * @author WuHao
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        ServletUtils.renderString(response
                , HttpStatus.ERROR
                , JSON.toJSONString(Result.fail(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权"))
        );
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(StringUtils.format("不支持的{}请求", e.getMethod()))));
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(ResultEnum.FAIL)));
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(ResultEnum.FAIL)));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public void handleBindException(BindException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(message)));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(message)));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public void handleServiceException(BusinessException e, HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(e.getCode(), e.getMessage())));
    }

    /**
     * 公共异常
     */
    @ExceptionHandler(CommonException.class)
    public void handleCommonException(CommonException e, HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(e.getCode(), e.getMessage())));
    }

    /**
     * 用户异常
     */
    @ExceptionHandler(UserException.class)
    public void handleUserException(UserException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.renderString(response, HttpStatus.SUCCESS, JSON.toJSONString(Result.fail(e.getCode(), e.getMessage())));
    }

}
