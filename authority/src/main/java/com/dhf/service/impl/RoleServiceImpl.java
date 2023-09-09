package com.dhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhf.domain.Role;
import com.dhf.service.RoleService;
import com.dhf.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author lenvoo
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2023-09-09 10:55:29
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




