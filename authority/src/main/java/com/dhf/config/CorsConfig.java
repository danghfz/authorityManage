package com.dhf.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 11:53
 * 跨域配置
 */
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*
         * 允许所有访问请求
         * 允许所有请求头
         * 允许所有请求方法
         * 允许所有请求来源
         */
        registry.addMapping("/**")
                .allowedHeaders("/**")
                .allowedMethods("/**")
                .allowedOrigins("/**");
    }
}
