package com.gzl0ng.securitydemo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author: guozhenglong
 * Date:2022/8/10 10:49
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123");

        auth.inMemoryAuthentication().withUser("lucy").password(password).roles("");
    }

    /**
     * 这是springsecurity提供的密码加密类，可以手动实现PasswordEncode进行加密
     * 需要手动创建这个加密对象  加@Bean注解
     * @return
     */
    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
}
