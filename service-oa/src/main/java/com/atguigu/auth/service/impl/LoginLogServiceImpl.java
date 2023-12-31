package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.LoginLogMapper;
import com.atguigu.model.system.SysLoginLog;
import com.atguigu.security.service.LoginLogService;
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
}
