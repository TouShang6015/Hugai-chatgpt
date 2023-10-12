package com.hugai.modules.draw.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskMjQueueManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskOpenaiQueueManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskSdQueueManager;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.draw.entity.dto.TaskDrawDTO;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.service.TaskDrawService;
import com.org.bebas.core.flowenum.utils.FlowEnumUtils;
import com.org.bebas.core.model.build.QueryFastLambda;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

    @ApiOperation(value = "获取任务明细")
    @GetMapping("/getTaskDetail/{drawType}")
    public Result getTaskList(@PathVariable String drawType) {
        DrawType drawTypeEnum = FlowEnumUtils.getEnumByKey(drawType.toUpperCase(Locale.ROOT), DrawType.class);
        Assert.notNull(drawTypeEnum, () -> new BusinessException("不匹配的绘图类型"));

        int runningCount = 0;
        int sum = 0;
        switch (drawTypeEnum) {
            case OPENAI -> {
                DrawTaskDataManager queueManager = SpringUtils.getBean(DrawTaskOpenaiQueueManager.class);
                runningCount = queueManager.getRunningCount();
                sum = queueManager.getWaitTime();
            }
            case SD -> {
                DrawTaskDataManager queueManager = SpringUtils.getBean(DrawTaskSdQueueManager.class);
                runningCount = queueManager.getRunningCount();
                sum = queueManager.getWaitTime();
            }
            case MJ -> {
                DrawTaskDataManager queueManager = SpringUtils.getBean(DrawTaskMjQueueManager.class);
                runningCount = queueManager.getRunningCount();
                sum = queueManager.getWaitTime();
            }
        }

        return Result.success()
                .put("runningCount", runningCount)
                .put("sum", sum)
                ;
    }

    @ApiOperation(value = "获取用户绘图任务列表")
    @GetMapping("/userTaskList/{drawType}")
    public Result userTaskList(@PathVariable String drawType, TaskDrawModel param) {
        param.setDrawType(drawType);
        param.setUserId(SecurityContextUtil.getUserId());
        QueryFastLambda.build(param).sortCondition(TaskDrawModel::getCreateTime, false);
        IPage<TaskDrawModel> page = taskDrawService.listPageByParam(PageUtil.pageBean(param), param);
        return Result.success(page);
    }

    @ApiOperation(value = "创建绘图任务")
    @PostMapping("/createTask/{apiKey}")
    public Result createTask(@PathVariable String apiKey, @RequestBody HashMap<String, Object> paramMap) {
        taskDrawService.createTask(apiKey, paramMap);
        return Result.success();
    }

    @ApiOperation(value = "绘图任务分页查询")
    @PostMapping("/baseQueryPageByParam")
    public Result baseQueryPageByParam(@RequestBody TaskDrawDTO param) {
        IPage<TaskDrawModel> pageDto = taskDrawService.listPageByParam(PageUtil.pageBean(param), param);
        return Result.success(pageDto);
    }

    @DeleteMapping("/baseDeleteByIds/{ids}")
    @ApiOperation(value = "删除任务")
    public Result baseDeleteByIds(@PathVariable("ids") String ids) {
        if (StrUtil.isEmpty(ids)) {
            return Result.fail("删除条件id不能为空");
        }
        String[] idsArr = ids.split(StringPool.COMMA);
        if (idsArr.length > 1000) {
            return Result.fail("不能批量删除超过1000个数据");
        }
        List<Long> idList = StringUtils.splitToList(ids, Long::valueOf);
        if (taskDrawService.removeByIds(idList)) {
            return Result.success(ResultEnum.SUCCESS_DELETE);
        }
        return Result.success(ResultEnum.FAIL_DELETE);
    }

}
