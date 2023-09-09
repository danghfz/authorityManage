package com.dhf.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 *
 * @TableName sys_user
 */
@TableName(value = "sys_user")
@Data
public class User implements Serializable, UserDetails {
    /**
     * 用户编号
     */
    @TableId
    private Long id;

    /**
     * 登录名称(用户名)
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 帐户是否过期(1-未过期，0-已过期)
     *  boolean 类型的 get方法 名称以 is开头
     *  accountNonExpired -> isAccountNonExpired()
     *  如果字段写成 accountNonExpired，就会在数据库中查询 account_non_expired找不到报错
     *  解决方式：
     *      1、字段写成 isAccountNonExpired，数据库中字段为 is_account_non_expired
     *         userMapper中的 isAccountNonExpired因为找不到 getIsAccountNonExpired(爆红但是可以使用)
     *         userMapper中的 isAccountNonExpired -> accountNonExpired
     *      2、@TableField(value = "is_account_non_expired")做映射
     */
    private boolean isAccountNonExpired;

    /**
     * 帐户是否被锁定(1-未过期，0-已过期)
     */
    private boolean isAccountNonLocked;

    /**
     * 密码是否过期(1-未过期，0-已过期)
     */
    private boolean isCredentialsNonExpired;

    /**
     * 帐户是否可用(1-可用，0-禁用)
     */
    private boolean isEnabled;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 所属部门ID
     */
    private Long departmentId;

    /**
     * 所属部门名称
     */
    private String departmentName;

    /**
     * 性别(0-男，1-女)
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 是否是管理员(1-管理员)
     */
    private Integer isAdmin;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否删除(0-未删除，1-已删除)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 权限列表
     */
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    /**
     * 查询用户权限列表
     */
    @TableField(exist = false)
    private List<Permission> permissionList;


}