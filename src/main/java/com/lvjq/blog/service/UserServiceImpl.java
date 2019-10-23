package com.lvjq.blog.service;

import com.lvjq.blog.dao.UserRepository;
import com.lvjq.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public User getUser(Long userId){
        return userRepository.getOne(userId);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
