package com.dhf.config.security.service;

import com.dhf.domain.Permission;
import com.dhf.domain.User;
import com.dhf.service.PermissionService;
import com.dhf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 18:25
 * 用户认证处理器
 */
@Service
public class CustomerUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final PermissionService permissionService;
    @Autowired
    CustomerUserDetailsServiceImpl(UserService userService,PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 查询用户拥有的权限列表
        List<Permission> permissions = permissionService.selectPermissionListByUserId(user.getId());
        // 获取权限编码
        List<String> collect = permissions.stream()
                // .filter(item -> item != null)
                .filter(Objects::nonNull)
                // .map(item -> item.getCode())
                .map(Permission::getCode)
                // .filter(item -> item != null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // 转换成数组
        String[] strings = collect.toArray(new String[collect.size()]);
        // 设置权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        // 设置菜单列表
        user.setPermissionList(permissions);
        return user;
    }
}
