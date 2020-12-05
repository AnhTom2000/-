package com.github.anhtom2000.service;

import com.github.anhtom2000.bean.User;

public interface UserService {
    public User findUserByName(String username);

    public void registerUser(User user);
}
