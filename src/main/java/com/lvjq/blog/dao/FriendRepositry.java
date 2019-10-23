package com.lvjq.blog.dao;

import com.lvjq.blog.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepositry extends JpaRepository<Friend,Long> {
}
