package com.lvjq.blog.service;


import com.lvjq.blog.dao.FriendRepositry;
import com.lvjq.blog.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepositry friendRepositry;

    @Override
    public Friend saveFriend(Friend friend) {
        return friendRepositry.save(friend);
    }

    @Override
    public void deleteFriend(Long id) {
        friendRepositry.deleteById(id);
    }

    @Override
    public Friend updateFriend(Friend friend) {
        return friendRepositry.save(friend);
    }

    @Override
    public Friend getFriend(Long id) {
        return friendRepositry.getOne(id);
    }

    @Override
    public List<Friend> listFriends() {
        return friendRepositry.findAll();
    }
}
