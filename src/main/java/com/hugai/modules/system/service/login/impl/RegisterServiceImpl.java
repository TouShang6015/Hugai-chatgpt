package com.hugai.modules.system.service.login.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.MessageCode;
import com.hugai.common.constants.SecurityConstant;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.system.entity.convert.SysUserConvert;
import com.hugai.modules.system.entity.dto.SysUserDTO;
import com.hugai.modules.system.entity.vo.auth.RegisterBody;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.system.service.ISysUserService;
import com.hugai.modules.system.service.login.IRegisterService;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static com.hugai.common.constants.MessageCode.System.SYSTEM_NOT_OPEN_REGISTER;


/**
 * @author WuHao
 * @date 2022/5/29 16:26
 */
@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements IRegisterService {

    private final ISysUserService sysUserService;

    private final IBaseResourceConfigService baseResourceConfigService;

    /**
     * 注册接口
     * <p>
     * userName -- 必填
     * nickName -- 必填
     * password -- 密码
     * </p>
     *
     * @param param
     * @return
     */
    @Override
    public Result doRegister(@Valid RegisterBody param) {
        ResourceMainVO resourceMainVO = baseResourceConfigService.getResourceMain();
        if (!resourceMainVO.getRegisterOpen())
            throw new BusinessException(MessageUtils.message(SYSTEM_NOT_OPEN_REGISTER));
        // 检验是否存在当前用户
        if (sysUserService.countByParam(SysUserDTO.builder().userName(param.getUserName()).build()) > 0) {
            return Result.fail(MessageUtils.message(MessageCode.User.USER_UNIQUE));
        }
        // 设置用户参数
        SysUserDTO _insertParam = SysUserConvert.INSTANCE.convertToRegister(param);
        _insertParam.setDeptId(1L);
        _insertParam.setRoleIds(CollUtil.newArrayList(SecurityConstant.SYSTEM_ID));
        OR.run(_insertParam.getPassword(), StrUtil::isNotEmpty, password -> _insertParam.setPassword(SecurityContextUtil.encryptPassword(password)));
        if (sysUserService.addUser(_insertParam)) {
            return Result.success(MessageUtils.message(MessageCode.User.USER_REGISTER_SUCCESS));
        }
        return Result.fail(MessageUtils.message(MessageCode.User.USER_REGISTER_FAIL));
    }
}
