package com.lvjq.blog.service;


import com.lvjq.blog.pojo.Friend;

import java.util.List;

public interface FriendService {

    Friend saveFriend(Friend friend);
    void deleteFriend(Long id);
    Friend updateFriend(Friend friend);
    Friend getFriend(Long id);

    List<Friend> listFriends();

}
