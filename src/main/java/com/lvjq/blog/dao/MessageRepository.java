package com.lvjq.blog.dao;


import com.lvjq.blog.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message,Long> {


}
