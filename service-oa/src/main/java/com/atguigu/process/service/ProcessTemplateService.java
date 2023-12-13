package com.atguigu.process.service;

import com.atguigu.model.process.ProcessTemplate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批模板 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-12-07
 */
public interface ProcessTemplateService extends IService<ProcessTemplate> {
    /**
     * 分页查询审批模板，把审批类型对应名称查询
     * @param pageParam
     * @return
     */
    IPage<ProcessTemplate> selectPageProcessTemplate(Page<ProcessTemplate> pageParam);
}
