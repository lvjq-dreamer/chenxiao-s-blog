package com.lvjq.blog.service;


import com.lvjq.blog.pojo.IPAddress;

import java.util.List;

public interface IPAddressService {

    IPAddress getOne(String IP);

    IPAddress addOne(IPAddress ipAddress);

    IPAddress updateOne(IPAddress ipAddress);

    List<IPAddress> getAll();

}
