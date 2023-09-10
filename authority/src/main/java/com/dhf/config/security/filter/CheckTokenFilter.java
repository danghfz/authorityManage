package com.dhf.config.security.filter;

import com.dhf.common.JwtUtils;
import com.dhf.config.redis.service.RedisService;
import com.dhf.config.security.exception.CustomerAuthenticationException;
import com.dhf.config.security.handler.LoginFailureHandler;
import com.dhf.config.security.service.CustomerUserDetailsServiceImpl;
import com.dhf.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 13:36
 * token 验证过滤器
 */
@Data
@Component
public class CheckTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsServiceImpl customerUserDetailsService;
    private final LoginFailureHandler loginFailureHandler;
    private final RedisService redisService;

    /**
     * 登录请求地址
     */
    @Value("${request.login.url}")
    private String loginUrl;

    @Autowired
    public CheckTokenFilter(JwtUtils jwtUtils, CustomerUserDetailsServiceImpl customerUserDetailsService, LoginFailureHandler loginFailureHandler, RedisService redisService) {
        this.jwtUtils = jwtUtils;
        this.customerUserDetailsService = customerUserDetailsService;
        this.loginFailureHandler = loginFailureHandler;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取当前请求地址
        String uri = request.getRequestURI();
        // 如果当前请求地址是登录请求地址，则直接放行
        if (!loginUrl.equals(uri)) {
            try {
                this.validateToken(request);
            } catch (AuthenticationException e) {
                loginFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
        // 放行
        doFilter(request, response, filterChain);
    }

    /**
     * 验证token
     *
     * @param request 请求
     */
    protected void validateToken(HttpServletRequest request) {
        // 从头部获取token
        String token = request.getHeader("token");
        // 如果请求头部没有获取到token，则从请求的参数中进行获取
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        //如果请求参数中也不存在token信息，则抛出异常
        if (ObjectUtils.isEmpty(token)) {
            throw new CustomerAuthenticationException("token不存在");
        }
        // 验证token是否有效
        // 自己设置 tokenKey, 根据存储自己的tokenKey
        String tokenKey = "token_" + token;
        String redisToken = redisService.get(tokenKey);
        //如果token和Redis中的token不一致，则验证失败
        if (!token.equals(redisToken)) {
            throw new CustomerAuthenticationException("token过期");
        }
        //如果存在token，则从token中解析出用户名
        String username = jwtUtils.getUsernameFromToken(token);
        if (ObjectUtils.isEmpty(username)) {
            throw new CustomerAuthenticationException("token解析失败");
        }
        // 获取用户信息
        User user = (User) customerUserDetailsService.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomerAuthenticationException("用户不存在");
        }
        // 创建身份验证对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
        authenticationToken
                .setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
        // 将身份验证对象放入安全上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
