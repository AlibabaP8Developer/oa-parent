package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.execption.GuiguException;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台登录登出
 * </p>
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        return sysUserService.login(loginVo);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
       return sysUserService.info(request);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

}