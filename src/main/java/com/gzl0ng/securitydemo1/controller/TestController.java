package com.gzl0ng.securitydemo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
