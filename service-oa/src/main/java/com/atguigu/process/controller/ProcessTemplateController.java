package com.atguigu.process.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.process.ProcessType;
import com.atguigu.process.service.ProcessTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 审批模板 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-12-07
 */
@Api(value = "审批类型", tags = "审批类型")
@RestController
@RequestMapping("/admin/process/processType")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessTemplateController {

    @Autowired
    private ProcessTypeService processTypeService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable("page") int page, @PathVariable("limit") int limit) {
        Page<ProcessType> pageParam = new Page<>(page, limit);
        Page<ProcessType> pageModel = processTypeService.page(pageParam);
        return Result.ok(pageModel);
    }

}

