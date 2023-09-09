package com.dhf.config.security.handler;

import com.alibaba.fastjson2.JSON;
import com.dhf.common.ResApi;
import com.dhf.common.ResCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
 * @date 2023/09/09 18:36
 * 登录认证失败处理器
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //设置客户端响应编码格式
        response.setContentType("application/json;charset=UTF-8");
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 提示信息
        String message = null;
        //判断异常类型
        if(exception instanceof AccountExpiredException){
            message = "账户过期,登录失败！";
        }else if(exception instanceof BadCredentialsException){
            message = "用户名或密码错误,登录失败！";
        }else if(exception instanceof CredentialsExpiredException){
            message = "密码过期,登录失败！";
        }else if(exception instanceof DisabledException){
            message = "账户被禁用,登录失败！";
        }else if(exception instanceof LockedException){
            message = "账户被锁,登录失败！";
        }else if(exception instanceof InternalAuthenticationServiceException){
            message = "账户不存在,登录失败！";
        }else{
            message = "登录失败！";
        }
        //将错误信息转换成JSON
        String result =
                JSON.toJSONString(ResApi.error().setCode(ResCode.ERROR.code()).setMsg(message));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
