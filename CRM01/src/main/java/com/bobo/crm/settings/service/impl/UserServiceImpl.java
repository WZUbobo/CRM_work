package com.bobo.crm.settings.service.impl;

import com.bobo.crm.exception.LoginException;
import com.bobo.crm.settings.dao.UserDao;
import com.bobo.crm.settings.domain.User;
import com.bobo.crm.settings.service.UserService;
import com.bobo.crm.utils.DateTimeUtil;
import com.bobo.crm.utils.SqlSessionUtil;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    public User login(String logAct, String logPwd, String ip) throws LoginException{
        Map<String,String> map = new HashMap<String, String>();
        map.put("loginAct",logAct);
        map.put("loginPwd",logPwd);

        User user = userDao.login(map);

        if (user==null) {
            throw new LoginException("账号密码错误");

        }
        //如果程序能够成功的执行到该行，说明账号密码正确
        //需要继续向下验证其他3项信息

        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){

            throw new LoginException("账号已失效");

        }

        //判断锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)){

            throw new LoginException("账号已锁定");

        }

        //判断ip地址
        String allowIps = user.getAllowIps();

        if(!allowIps.contains(ip)){

            throw new LoginException("ip地址受限");

        }
        return user;
    }

    public List<User> getUserList() {
        List<User> uList = userDao.getUserList();

        return uList;
    }

}