package com.hugai.modules.draw.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuHao
 * @since 2023/9/7 17:22
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.DRAW + "/task")
@Api(tags = "绘画控制器")
public class DrawTaskController {
}
