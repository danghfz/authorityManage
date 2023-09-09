package com.dhf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhf.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lenvoo
 * @description 针对表【sys_permission(系统权限表)】的数据库操作Service
 * @createDate 2023-09-09 10:55:29
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户 id 查询权限菜单列表
     *
     * @param userId 用户 id
     * @return 权限菜单列表
     */
    List<Permission> selectPermissionListByUserId(Long userId);
}
