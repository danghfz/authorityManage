package com.dhf.controller;

import com.dhf.common.ResApi;
import com.dhf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 11:39
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户列表
     */
    @GetMapping("/list")
    public ResApi list() {
        return ResApi.success("userList",userService.list());
    }
}
