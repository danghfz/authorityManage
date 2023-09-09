package com.dhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhf.domain.Permission;
import com.dhf.service.PermissionService;
import com.dhf.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author lenvoo
* @description 针对表【sys_permission(系统权限表)】的数据库操作Service实现
* @createDate 2023-09-09 10:55:29
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




