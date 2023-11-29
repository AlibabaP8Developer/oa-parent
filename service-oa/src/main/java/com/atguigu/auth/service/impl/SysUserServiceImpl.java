package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.execption.GuiguException;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        if (status.intValue() == 1) {
            sysUser.setStatus(status);
        } else {
            sysUser.setStatus(0);
        }
        this.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    @Override
    public Result login(LoginVo loginVo) {
        // 1 获取输入用户名和密码
        String username = loginVo.getUsername();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(username), SysUser::getUsername, username);
        SysUser sysUser = this.getOne(queryWrapper);
        // 2.根据用户名查询数据库
        if (sysUser == null) {
            throw new GuiguException(201, "用户名不存在");
        }
        // 3.用户信息是否存在

        // 4.判断密码
        String passwordInput = loginVo.getPassword();
        String passwordDb = sysUser.getPassword();
        String encryptPI = MD5.encrypt(passwordInput);
        if (!encryptPI.equals(passwordDb)) {
            throw new GuiguException(201, "用户名或密码错误");
        }
        // 5.判断用户是否被禁用
        if (sysUser.getStatus() == 0) {
            throw new GuiguException(201, "用户名已被禁用");
        }
        // 6.使用jwt根据用户ID和用户名称生成token字符串
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        // 7.返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    @Override
    public Result info(HttpServletRequest request) {
        // 1.从请求头获取用户信息 （获取请求头token字符串）
        String tooken = request.getHeader("header");

        // 2.从token字符串获取用户ID 或 用户名
        Long userId = JwtHelper.getUserId(tooken);
        // 3.根据用户ID查询数据库
        SysUser sysUser = this.getById(userId);

        // 4.根据用户ID获取用户可以操作菜单列表
        // 查询数据库动态构建路由结构，进行显示
         List<RouterVo> routerList =sysMenuService.findUserMenuListByUserId(userId);

        // 5.根据用户ID获取用户可以操作菜单列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        // 6.返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name", sysUser.getName());
        map.put("avatar",sysUser.getHeadUrl());
        // 返回用户可以操作按钮
        map.put("buttons", permsList);
        // 返回用户可以操作菜单
        map.put("routers", routerList);
        return Result.ok(map);
    }
}