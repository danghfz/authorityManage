package com.dhf.config.security.handler;

import com.alibaba.fastjson2.JSON;
import com.dhf.common.ResApi;
import com.dhf.common.ResCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
 * @date 2023/09/09 18:42
 * 匿名用户访问无权限资源时处理器
 */
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 转换成json字符串
        String res = JSON.toJSONString(
                ResApi.error()
                        .setCode(ResCode.NO_AUTH)
                        .setMsg("无权限访问,请联系管理员！")
        );
        outputStream.write(res.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
