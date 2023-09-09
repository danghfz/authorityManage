package com.dhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhf.domain.User;
import com.dhf.service.UserService;
import com.dhf.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lenvoo
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-09-09 10:55:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




