package com.hugai.modules.user.service.impl;

import com.hugai.modules.user.mapper.UserInfoMapper;
import com.hugai.modules.user.service.UserInfoAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author WuHao
 * @since 2023/10/11 14:37
 */
@RequiredArgsConstructor
@Service
public class UserInfoAdminServiceImpl implements UserInfoAdminService {

    private final UserInfoMapper userInfoMapper;

}
