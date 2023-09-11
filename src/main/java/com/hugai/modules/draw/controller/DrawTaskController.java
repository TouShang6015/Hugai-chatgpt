package com.hugai.modules.draw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.RedisCacheKey;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.service.TaskDrawService;
import com.org.bebas.core.model.build.QueryFastLambda;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

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

    private final RedisTemplate redisTemplate;

    @ApiOperation(value = "获取当前任务明细")
    @GetMapping("/getTaskDetail/{drawType}")
    public Result getTaskList(@PathVariable String drawType) {
        Long userId = SecurityContextUtil.getUserId();
        Long taskSize = redisTemplate.opsForZSet().size(RedisCacheKey.TASK_DRAW_QUEUE_OPENAI);
        Long userIndex = redisTemplate.opsForZSet().rank(RedisCacheKey.TASK_DRAW_QUEUE_OPENAI, userId);
        if (Objects.nonNull(userIndex)) {
            userIndex += 1;
        }
        return Result.success()
                .put("taskSize", taskSize)
                .put("userIndex", userIndex)
                ;
    }

    @ApiOperation(value = "获取用户绘图任务列表")
    @GetMapping("/userTaskList/{drawType}")
    public Result userTaskList(@PathVariable String drawType, TaskDrawModel param) {
        param.setDrawType(drawType);
        param.setUserId(SecurityContextUtil.getUserId());
        QueryFastLambda.build(param).sortCondition(TaskDrawModel::getCreateTime,false);
        IPage<TaskDrawModel> page = taskDrawService.listPageByParam(PageUtil.pageBean(param), param);
        return Result.success(page);
    }

    @ApiOperation(value = "创建绘图任务")
    @PostMapping("/createTask/{apiKey}")
    public Result createTaskOpenai(@PathVariable String apiKey, @RequestBody HashMap<String, Object> paramMap) {
        taskDrawService.createTask(apiKey, paramMap);
        return Result.success();
    }

}
