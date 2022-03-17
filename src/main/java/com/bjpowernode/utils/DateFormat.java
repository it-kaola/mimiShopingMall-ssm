package com.bjpowernode.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    // 获取当前时间，格式为：yyyy-MM-dd，一共10位
    public static String TenDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

}
