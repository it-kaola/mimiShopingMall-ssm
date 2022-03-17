package com.bjpowernode.service;

import com.bjpowernode.domain.Admin;

public interface AdminService {
    // 登录验证功能
    Admin loginVerify(String username, String password);
}
