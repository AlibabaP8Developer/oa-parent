package com.atguigu.auth.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param  assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);

    List<RouterVo> findUserMenuListByUserId(Long userId);

    List<String> findUserPermsByUserId(Long userId);
}