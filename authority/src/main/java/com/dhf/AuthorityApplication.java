package com.dhf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 10:48
 */
@SpringBootApplication
@MapperScan("com.dhf.mapper")
public class AuthorityApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }
}
