package com.bobo.settings.test;

import com.bobo.crm.utils.DateTimeUtil;

public class Test1 {
    public static void main(String[] args) {
        //验证失效时间
        //失效时间
        /*String expireTime = "2022-02-02 22:22:22";
        //当前系统时间
        String currentTime = DateTimeUtil.getSysTime();
        int count =  expireTime.compareTo(currentTime);*/

        String lockState = "0";
        if ("0".equals(lockState)){
            System.out.println("账号已锁定");
        }
    }
}
