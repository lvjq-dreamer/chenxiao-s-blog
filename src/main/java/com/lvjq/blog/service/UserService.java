package com.lvjq.blog.service;


import com.lvjq.blog.pojo.User;

public interface UserService {


    User checkUser(String username, String password);

    User getUser(Long userId);

    void save(User user);
}
