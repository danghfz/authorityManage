package com.dhf.config.security.handler;

import com.alibaba.fastjson2.JSON;
import com.dhf.common.JwtUtils;
import com.dhf.common.LoginResult;
import com.dhf.common.ResCode;
import com.dhf.config.redis.service.RedisService;
import com.dhf.domain.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 18:28
 * 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final RedisService redisService;
    @Autowired
    public LoginSuccessHandler(JwtUtils jwtUtils,
                               RedisService redisService){
        this.jwtUtils = jwtUtils;
        this.redisService = redisService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        //获取当登录用户信息
        User user = (User) authentication.getPrincipal();
        // 生成token
        String token = jwtUtils.generateToken(user);
        // 设置token密钥及过期时间
        long expireTime = Jwts.parser()
                // 设置密钥
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(token.replace("jwt_", ""))
                // 获取过期时间
                .getBody().getExpiration().getTime();
        // 创建LoginResult登录结果对象
        LoginResult loginResult = new LoginResult(user.getId(), ResCode.SUCCESS.code(), token, expireTime);
        //将对象转换为json字符串
        String result = JSON.toJSONString(loginResult);
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        // 将 token 存入 Redis
        redisService.set("token_"+token,token,jwtUtils.getExpiration() / 1000);
    }

}
