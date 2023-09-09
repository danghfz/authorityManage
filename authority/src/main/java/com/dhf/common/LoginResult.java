package com.dhf.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 19:27
 * 封装token返回的数据信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private Long id;
    private int code;
    private String token;
    /**
     * 过期时间
     */
    private Long expireTime;
}
