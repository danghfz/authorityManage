package com.dhf.controller.sys;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dhf.common.JwtUtils;
import com.dhf.common.ResApi;
import com.dhf.config.redis.service.RedisService;
import com.dhf.domain.User;
import com.dhf.vo.TokenVo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
        String oldTokenKey = "token_" + token;
        redisService.del(oldTokenKey);
        //存储新的token
        String newTokenKey = "token_" + reToken;
        redisService.set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000);
        //创建TokenVo对象
        TokenVo tokenVo = new TokenVo(expireTime, reToken);
        //返回数据
        return ResApi.success("token", tokenVo);
    }
}