package com.gzl0ng.securitydemo1.service;

import com.gzl0ng.securitydemo1.config.SpringDataJPAConfig;
import com.gzl0ng.securitydemo1.entity.Users;
import com.gzl0ng.securitydemo1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

/**
 * Author: guozhenglong
 * Date:2022/8/10 11:09
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //调用usermapper方法,根据用户名查询数据库
//        QueryWrapper<Users> wrapper = new QueryWrapper<>();
//        //where username =?
//        wrapper.eq("username",username);
//        Users users = userMapper.selectOne(wrapper);
//        //判定
//        if (users == null){
//            //数据库没有这个对象
//            throw  new UsernameNotFoundException("用户名不存在");
//        }
//
//        //这里手动创建一个权限集合
//        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");

        //jpa
        Users users = userMapper.findByUsername(username);
        if (users == null){
            //数据库没有这个对象
            throw  new UsernameNotFoundException("用户名不存在");
        }
        //手动创建一个集合，实际也是从数据库查
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");

        //从查询数据库返回users对象，得到用户名和密码，返回
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
