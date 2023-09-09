package com.dhf.config.security.service;

import com.dhf.domain.User;
import com.dhf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 18:25
 * 用户认证处理器
 */
@Service
public class CustomerUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    CustomerUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
