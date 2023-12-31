package com.dhf.service;

import com.dhf.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lenvoo
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-09-09 10:55:29
*/
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return
     */
    User findUserByUserName(String userName);

}
