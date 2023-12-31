package com.dhf.controller.sys;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dhf.common.JwtUtils;
import com.dhf.common.MenuTree;
import com.dhf.common.ResApi;
import com.dhf.config.redis.service.RedisService;
import com.dhf.domain.Permission;
import com.dhf.domain.User;
import com.dhf.entity.UserInfo;
import com.dhf.vo.RouterVo;
import com.dhf.vo.TokenVo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 15:36
 */
@ResponseBody
@Controller
@RequestMapping("/sys/user")
public class SysUserController {
    private final RedisService redisService;
    private final JwtUtils jwtUtils;

    @Autowired
    SysUserController(RedisService redisService, JwtUtils jwtUtils) {
        this.redisService = redisService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 刷新token
     *
     * @param request 请求
     * @return ResApi
     */
    @PostMapping("/refreshToken")
    public ResApi refreshToken(HttpServletRequest request) {
        // 从 header 中获取 token
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        // 从 security 上下文中获取当前用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        // 获取当前用户信息
        User user = (User) authentication.getPrincipal();
        // 重新生成token
        String reToken = "";
        if (jwtUtils.validateToken(token, user)) {
            reToken = jwtUtils.refreshToken(token);
        }
        //获取本次token的到期时间，交给前端做判断
        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(reToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token信息
        String oldTokenKey = jwtUtils.getRedisPrefix() + token;
        redisService.del(oldTokenKey);
        //存储新的token
        String newTokenKey = jwtUtils.getRedisPrefix() + reToken;
        redisService.set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000);
        //创建TokenVo对象
        TokenVo tokenVo = new TokenVo(expireTime, reToken);
        //返回数据
        return ResApi.success("token", tokenVo);
    }

    /**
     * 获取用户信息
     *
     * @return ResApi
     */
    @GetMapping("/info")
    public ResApi getInfo() {
        // 从 security 上下文中获取当前用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return ResApi.error().setMsg("获取用户信息失败");
        }
        // 获取当前用户信息
        User user = (User) authentication.getPrincipal();
        // 获取当前用户角色权限信息
        List<Permission> permissionList = user.getPermissionList();
        // 获取权限编码
        String[] permissions = permissionList.stream()
                .filter(Objects::nonNull)
                .map(Permission::getCode)
                .toArray(String[]::new);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), user.getAvatar(), null, permissions);

        return ResApi.success("userInfo", userInfo).setMsg("获取用户信息成功");
    }

    /**
     * 退出登录
     *
     * @param request  req
     * @param response resp
     * @return ResApi
     */
    @PostMapping("/logout")
    public ResApi logout(HttpServletRequest request, HttpServletResponse response) {
        // 获取token
        String token = request.getParameter("token");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getHeader("token");
        }
        // 获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 清空用户信息
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            // 清空 redis信息
            redisService.del(jwtUtils.getRedisPrefix() + token);
        }
        return ResApi.success().setMsg("退出成功");
    }

    @GetMapping("/getMenuList")
    public ResApi getMenuList() {
        // 从 springSecurity 获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return ResApi.error().setMsg("获取用户信息失败");
        }
        User user = (User) authentication.getPrincipal();
        // 获取权限
        List<Permission> permissionList = user.getPermissionList();
        // 筛选目录和菜单 权限类型(0-目录 1-菜单 2-按钮)
        List<Permission> permissions = permissionList.stream()
                .filter(item -> item != null && item.getType() != 2)
                .collect(Collectors.toList());
        // 生成路由
        List<RouterVo> routerVos = MenuTree.makeRouter(permissions, 0L);
        return ResApi.success("routerVos", routerVos);
    }

}
