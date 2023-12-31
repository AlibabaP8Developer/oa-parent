package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.LoginLogMapper;
import com.atguigu.model.system.SysLoginLog;
import com.atguigu.security.service.LoginLogService;
import com.atguigu.vo.system.SysLoginLogQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void recordLoginLog(String username, Integer status, String ipAddr, String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setMsg(message);
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipAddr);
        loginLogMapper.insert(sysLoginLog);
    }

    @Override
    public IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo) {
        // 创建Page对象
        Page<SysLoginLog> pageParam = new Page<>(page, limit);
        // 获取条件值
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();
        // 封装条件
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), SysLoginLog::getUsername, username);
        // >=
        queryWrapper.ge(StringUtils.isNotBlank(createTimeBegin), SysLoginLog::getCreateTime, createTimeBegin);
        queryWrapper.le(StringUtils.isNotBlank(createTimeEnd), SysLoginLog::getCreateTime, createTimeEnd);
        return loginLogMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public SysLoginLog getById(Long id) {
        return null;
    }
}
