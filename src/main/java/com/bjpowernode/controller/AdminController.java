package com.bjpowernode.controller;


import com.bjpowernode.domain.Admin;
import com.bjpowernode.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


// 用户控制器

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminServiceProxy; // 实际的到的是代理对象

    @RequestMapping("/login.action")
    public String loginVertify(HttpServletRequest request, String name, String pwd){

        Admin admin = adminServiceProxy.loginVerify(name, pwd);

        if(admin == null){
            // 如果admin为空，表示用户名或密码错误
            request.setAttribute("errmsg", "用户名或密码错误");
            return "login"; // 跳转到登录页面
        }

        // 程序执行到此处，说明有对应的用户
        request.setAttribute("admin", admin);
        return "main";
    }
}
