package com.gzl0ng.securitydemo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Author: guozhenglong
 * Date:2022/8/10 11:05
 */
@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    //注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //在启动时创建token表
//        jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

/*
配置免密登录
 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置注销登录的配置
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

        //配置没有访问权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");


        http.formLogin()//自定义编写的登录页面
                .loginPage("/login.html")//登录页面设置
                .loginProcessingUrl("/user/login")//登录访问路径  跳到对应controller(这里没写登录页的controller)
                .defaultSuccessUrl("/success.html").permitAll()//登录成功之后，跳转路径
                .and().authorizeRequests()
//                    .antMatchers("/","/test/hello","/user/login").permitAll()//设置哪些路径可以直接访问
                    //当前登录的用户，只要具有admin权限才可以访问这个路径
                //1.
//                    .antMatchers("/test/index").hasAuthority("admins")
                //2.
//                    .antMatchers("/test/index").hasAnyAuthority("admins,manager")
                //3.   加了前缀   ROLE_sale
//                .antMatchers("test/index").hasRole("sale")
                    .anyRequest().authenticated()//配置所有路径不需要认证
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60)//设置有效时长,单位秒
                .userDetailsService(userDetailsService)//底层操作数据库的对象
                .and().csrf().disable();//关闭csrf防护
    }


}
