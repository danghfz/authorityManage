package com.dhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhf.domain.Permission;
import com.dhf.mapper.PermissionMapper;
import com.dhf.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lenvoo
 * @description 针对表【sys_permission(系统权限表)】的数据库操作Service实现
 * @createDate 2023-09-09 10:55:29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionService {
    private final PermissionMapper permissionMapper;

    @Autowired
    PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionListByUserId(Long userId) {
        return permissionMapper.selectPermissionListByUserId(userId);
    }
}




