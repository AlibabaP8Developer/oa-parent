package com.atguigu.auth.service;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);

    SysUser getByUsername(String username);

    Result login(LoginVo loginVo);

    Result info(HttpServletRequest request);
}