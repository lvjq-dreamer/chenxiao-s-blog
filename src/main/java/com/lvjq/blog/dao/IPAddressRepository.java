package com.lvjq.blog.dao;

import com.lvjq.blog.pojo.IPAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPAddressRepository extends JpaRepository<IPAddress,Long> {

    IPAddress getIPAddressByThisIP(String thisIP);

}
