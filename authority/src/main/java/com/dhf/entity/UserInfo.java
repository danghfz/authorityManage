package com.dhf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 16:10
 * 要和前端页面显示的一样
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 个人简介
     */
    private String introduction;
    /**
     * 角色权限集合
     */
    private Object[] roles;

}
