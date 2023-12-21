package com.atguigu.process.service;

import com.atguigu.model.process.Process;
import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-12-20
 */
public interface OaProcessService extends IService<Process> {

    /**
     * 审批管理列表
     * @param pageParam
     * @param processQueryVo
     * @return
     */
    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);

    /**
     * 部署流程定义
     * @param deployPath
     */
     void deployByZip(String deployPath);
}
