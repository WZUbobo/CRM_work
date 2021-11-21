package com.bobo.crm.settings.service;

import com.bobo.crm.exception.LoginException;
import com.bobo.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String logAct, String logPwd, String ip) throws LoginException;

    List<User> getUserList();
}

