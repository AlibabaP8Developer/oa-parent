package com.atguigu.security.service;

public interface LoginLogService{
    void recordLoginLog(String username, Integer status, String ipAddr, String message);
}
