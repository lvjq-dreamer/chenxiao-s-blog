package com.lvjq.blog.dao;

import com.lvjq.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    User findUserByUsernameAndPassword(String username, String password);

}
