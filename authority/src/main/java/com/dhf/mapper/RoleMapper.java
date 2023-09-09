package com.dhf.mapper;

import com.dhf.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lenvoo
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-09-09 10:55:29
* @Entity com.dhf.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




