package com.hugai.modules.draw.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.modules.draw.service.TaskDrawService;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    private final TaskDrawService taskDrawService;

    @PostMapping("/createTask/{apiKey}")
    public Result createTaskOpenai(@PathVariable String apiKey, @RequestBody HashMap<String, Object> paramMap) {
        taskDrawService.createTask(apiKey, paramMap);
        return Result.success();
    }

}
