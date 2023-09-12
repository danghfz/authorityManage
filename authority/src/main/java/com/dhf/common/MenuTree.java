package com.dhf.common;

import com.dhf.domain.Permission;
import com.dhf.vo.RouterVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 16:54
 * 生成菜单树
 */
public class MenuTree {
    /**
     * 生成路由树
     *
     * @param meuList 菜单列表
     * @param pid     父级id
     * @return 路由列表
     */
    public static List<RouterVo> makeRouter(List<Permission> meuList, Long pid) {
        // 创建集合保存路由列表
        List<RouterVo> routerList = new ArrayList<>();
        // 判断meuList是否为空 如果为空则返回一个空集合
        Optional.ofNullable(meuList).orElse(new ArrayList<>())
                .stream()
                // 筛选不为空的菜单及菜单父id相同的数据
                .filter(item -> item != null && item.getParentId().equals(pid))
                // 创建路由对象
                .forEach(item -> {
                    RouterVo router = new RouterVo();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    // 判断是否是一级菜单
                    if (item.getParentId().equals(0L)) {
                        // 一级菜单组件
                        router.setComponent("Layout");
                        // 显示路由
                        router.setAlwaysShow(true);
                    } else {
                        // 具体的组件
                        router.setComponent(item.getUrl());
                        // 折叠路由
                        router.setAlwaysShow(false);
                    }
                    // 设置meta信息
                    router.setMeta(
                            router.new Meta(item.getLabel(), item.getIcon(), item.getCode().split(",")));
                    // 递归生成路由
                    router.setChildren(makeRouter(meuList, item.getId()));
                    // 添加到路由列表
                    routerList.add(router);
                });
        return routerList;
    }

    /**
     * 生成菜单树
     *
     * @param meuList 菜单列表
     * @param pid     父级id
     * @return 菜单树
     */
    public static List<Permission> makeMenuTree(List<Permission> meuList, Long pid) {
        if (meuList == null) {
            return new ArrayList<>();
        }
        // 创建集合保存菜单
        ArrayList<Permission> permissions = new ArrayList<>();
        List<Permission> parentList = new ArrayList<>();
        // 找到父级菜单
        for (Permission permission : meuList) {
            if (permission != null && permission.getParentId().equals(pid)) {
                parentList.add(permission);
            }
        }
        // 遍历父级菜单
        for (Permission parent : parentList) {
            // 递归生成子菜单
            parent.setChildren(makeMenuTree(meuList, parent.getId()));
            // 添加到菜单列表
            permissions.add(parent);
        }
        return permissions;

    }
}
