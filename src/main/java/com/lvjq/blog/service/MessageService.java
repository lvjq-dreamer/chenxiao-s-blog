package com.lvjq.blog.service;


import com.lvjq.blog.pojo.Message;

import java.util.List;

public interface MessageService {


    List<Message> listMessages();


    Message saveMessage(Message message);

}
