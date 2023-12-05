package com.hugai.framework.log.aspectj;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.ChannelEnum;
import com.hugai.framework.log.annotation.Log;
import com.hugai.framework.asyncMessage.MessageService;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.common.modules.entity.system.model.SysOperLogModel;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.ip.AddressUtils;
import com.org.bebas.utils.ip.IpUtils;
import io.swagger.annotations.Api;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 操作日志记录处理
 *
 * @author WhHao
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private MessageService messageService;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(logAnnotation)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log logAnnotation, Object jsonResult) {
        handleLog(joinPoint, logAnnotation, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(logAnnotation)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log logAnnotation, Exception e) {
        handleLog(joinPoint, logAnnotation, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            LoginUserContextBean loginUser = Optional.ofNullable(SecurityContextUtil.getLoginUserNotThrow()).orElseGet(LoginUserContextBean::new);

            // *========数据库日志=========*//
            SysOperLogModel operLog = new SysOperLogModel();
            operLog.setStatus(Integer.valueOf(Constants.Status.NORMAL));
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            operLog.setOperName(loginUser.getUsername());

            if (e != null) {
                operLog.setStatus(Integer.valueOf(Constants.Status.NO_NORMAL));
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + StringPool.DOT + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 设置操作地点
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
            // 保存数据库
            messageService.send(ChannelEnum.log_handle, JSON.toJSONString(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLogModel operLog, Object jsonResult) throws Exception {
        StringBuilder title = new StringBuilder(StringUtils.EMPTY);
        Annotation[] annotations = joinPoint.getTarget().getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Api) {
                String[] tags = ((Api) annotation).tags();
                title.append(tags[0]);
            }
        }
        title.append(" [");
        title.append(log.title());
        title.append("]");
        // 设置标题
        operLog.setTitle(title.toString());
        // 设置操作人类别
        operLog.setOperatorType(null);
        setRequestValue(joinPoint, operLog);
        operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLogModel operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
