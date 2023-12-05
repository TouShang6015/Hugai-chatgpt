package com.hugai.core.sd.valid;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.sd.valid.annotation.CheckSDHostConnect;
import com.hugai.common.entity.baseResource.ResourceDrawVO;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author WuHao
 * @since 2023/10/10 15:39
 */
public class SDCheckConnectValidator implements ConstraintValidator<CheckSDHostConnect, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ResourceDrawVO resourceDraw = SpringUtils.getBean(BaseResourceWebApi.class).getResourceDraw();
        String sdHostUrl = resourceDraw.getSdHostUrl();
        Assert.notEmpty(sdHostUrl, () -> new BusinessException("暂未开启SD绘图功能"));
        // 验证连接
        if (!verifyUrlConnect(sdHostUrl + "/user", 1000 * 3)) {
            return false;
        }
        return true;
    }

    public static boolean verifyUrlConnect(String urlString, int timeOutMillSeconds) {
        String response = HttpUtil.get(urlString, timeOutMillSeconds);
        if (StrUtil.NULL.equals(response)) {
            return true;
        }
        return false;
    }

}
