package com.dhf.mapper;

import com.dhf.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
* @author lenvoo
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-09-09 10:55:29
* @Entity com.dhf.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




