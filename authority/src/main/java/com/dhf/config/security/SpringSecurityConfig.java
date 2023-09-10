package com.dhf.config.security;

import com.dhf.config.security.filter.CheckTokenFilter;
import com.dhf.config.security.handler.AnonymousAuthenticationHandler;
import com.dhf.config.security.handler.CustomerAccessDeniedHandler;
import com.dhf.config.security.handler.LoginFailureHandler;
import com.dhf.config.security.handler.LoginSuccessHandler;
import com.dhf.config.security.service.CustomerUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 18:45
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final CustomerAccessDeniedHandler customerAccessDeniedHandler;
    private final AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    private final CustomerUserDetailsServiceImpl customerUserDetailsService;
    private final CheckTokenFilter checkTokenFilter;

    @Autowired
    SpringSecurityConfig(LoginSuccessHandler loginSuccessHandler,
                         LoginFailureHandler loginFailureHandler,
                         CustomerAccessDeniedHandler customerAccessDeniedHandler,
                         AnonymousAuthenticationHandler anonymousAuthenticationHandler,
                         CustomerUserDetailsServiceImpl customerUserDetailsService,
                         CheckTokenFilter checkTokenFilter) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.customerAccessDeniedHandler = customerAccessDeniedHandler;
        this.anonymousAuthenticationHandler = anonymousAuthenticationHandler;
        this.customerUserDetailsService = customerUserDetailsService;
        this.checkTokenFilter = checkTokenFilter;
    }
    @Value("${request.login.url}")
    private String loginUrl;
    /**
     * 处理登录认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录前过滤验证
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 登录过程处理
        http.formLogin()
                // 登录请求url
                .loginProcessingUrl(loginUrl)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                // 禁用csrf防御机制
                .csrf().disable()
                // 不创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 设置需要拦截的请求
                .authorizeRequests()
                // 放行登录请求，不拦截
                .antMatchers(loginUrl).permitAll()
                // 其他拦截
                .anyRequest().authenticated()
                .and()
                // 异常处理
                .exceptionHandling()
                // 匿名用户访问无权限资源时处理器
                .authenticationEntryPoint(anonymousAuthenticationHandler)
                // 认证用户访问无权限资源时处理器
                .accessDeniedHandler(customerAccessDeniedHandler)
                .and().cors();//开启跨域配置
    }

    /**
     * 配置认证处理器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
