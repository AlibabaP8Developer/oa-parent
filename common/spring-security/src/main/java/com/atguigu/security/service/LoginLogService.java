package com.atguigu.security.service;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.vo.system.SysLoginLogQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface LoginLogService{
    // 登录日志
    void recordLoginLog(String username, Integer status, String ipAddr, String message);

    // 条件分页查询登录日志
    IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo);

    SysLoginLog getById(Long id);
}
