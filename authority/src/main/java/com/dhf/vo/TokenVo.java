package com.dhf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 15:34
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo {
    /**
     * 过期时间
     */
    private long expireTime;
    /**
     * token
     */
    private String token;
}
