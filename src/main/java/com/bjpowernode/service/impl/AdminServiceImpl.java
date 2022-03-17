package com.bjpowernode.service.impl;

import com.bjpowernode.dao.AdminDao;
import com.bjpowernode.domain.Admin;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {


    // 数据库访问对象
    @Resource
    private AdminDao adminDao;

    @Override
    public Admin loginVerify(String username, String password) {

        // 向对密码进行MD5加密处理
        password = MD5Util.getMD5(password);

        Admin admin = adminDao.selectOneAdmin(username, password);

        return admin;
    }
}
