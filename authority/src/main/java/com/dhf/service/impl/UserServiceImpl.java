package com.dhf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhf.domain.User;
import com.dhf.mapper.UserMapper;
import com.dhf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lenvoo
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-09-09 10:55:29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    private final UserMapper userMapper;

    @Autowired
    UserServiceImpl(UserMapper mapper) {
        this.userMapper = mapper;
    }

    @Override
    public User findUserByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userName);
        return userMapper.selectOne(wrapper);
    }
}




