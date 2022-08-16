package com.gzl0ng.securitydemo1.controller;

import com.gzl0ng.securitydemo1.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭正龙
 * @date 2022-08-07
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("hello")
    public String add(){
        return "hello security";
    }

    @GetMapping("index")
    public String index(){
        return "hello index";
    }

    @GetMapping("update")
    //用户只要有其中一个角色即可
//    @Secured({"ROLE_sale","ROLE_manager"})
//    @PreAuthorize("hasAnyAuthority('admins')")
    @PostAuthorize("hasAnyAuthority('admins')")
    public String update(){
        System.out.println("update......");
        return "hello update";
    }

    @GetMapping("getAll")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.username == 'admin1'")
    public List<Users> getAllUser(){
        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(1L,"admin1","6666"));
        list.add(new Users(2L,"admin2","888"));

        System.out.println(list);
        return list;
    }
}
