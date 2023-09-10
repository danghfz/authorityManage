package com.dhf.config.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 14:21
 * 自定义异常
 */
public class CustomerAuthenticationException extends AuthenticationException {
    public CustomerAuthenticationException(String message) {
        super(message);
    }
}
