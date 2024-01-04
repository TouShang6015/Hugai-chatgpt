package com.hugai.modules.user.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.user.socket.manager.UserSocketGlobalData;
import com.hugai.core.websocket.endpoint.SocketPointUser;
import com.hugai.core.websocket.pool.UserSocketPool;
import com.org.bebas.core.function.OR;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.util.Map;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/10/16 10:16
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.USER + "/connect")
@Api(value = "用户连接管理", tags = "用户连接管理")
public class SocketUserController {

    @GetMapping("/getOnlineCount")
    @ApiOperation(value = "获取当前在线人数")
    public Result getOnlineCount() {
        return Result.success(UserSocketGlobalData.onlineAmount);
    }

    @GetMapping("/closeSocket/{sessionId}")
    @ApiOperation(value = "手动清除socket连接")
    public Result closeSocket(@PathVariable("sessionId") String sessionId) {
        OR.run(UserSocketPool.get(String.valueOf(SecurityContextUtil.getUserId()), sessionId), Objects::nonNull, SocketPointUser::closeSession);
        log.info("手动清除socket连接 - sessionId: {}", sessionId);
        return Result.success();
    }

    @GetMapping("/verify/{sessionId}")
    @ApiOperation(value = "验证socket连接")
    public Result verify(@PathVariable("sessionId") String sessionId) {
        if (StrUtil.isEmpty(sessionId)) {
            return Result.success();
        }
        Long userId = SecurityContextUtil.getUserId();
        Map<String, SocketPointUser> map = UserSocketPool.get(String.valueOf(userId));
        if (CollUtil.isEmpty(map)) {
            return Result.fail();
        }
        if (!map.containsKey(sessionId)) {
            return Result.fail();
        }
        SocketPointUser socketPointUser = map.get(sessionId);
        Session session = socketPointUser.getSession();
        if (!session.isOpen()) {
            return Result.fail();
        }
        return Result.success();
    }
}
