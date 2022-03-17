package com.bjpowernode.dao;

import com.bjpowernode.domain.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {

    // 根据用户名和密码查找对应的用户信息
    Admin selectOneAdmin(@Param("aName") String username, @Param("aPass") String password);
}