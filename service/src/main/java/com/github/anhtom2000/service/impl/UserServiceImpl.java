package com.github.anhtom2000.service.impl;

import com.github.anhtom2000.bean.User;
import com.github.anhtom2000.dao.UserDao;
import com.github.anhtom2000.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    public void registerUser(User user) {
        userDao.register(user);
    }
}
