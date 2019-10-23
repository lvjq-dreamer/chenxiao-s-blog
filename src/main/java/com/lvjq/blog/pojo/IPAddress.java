package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_IP")
public class IPAddress {

    @Id
    @GeneratedValue
    private Long id;

    private String thisIP;

    private String address;

    private int views;

    @Temporal(TemporalType.TIMESTAMP)
    private Date viewTime;

    public IPAddress(String thisIP, String address, int views, Date viewTime) {
        this.thisIP = thisIP;
        this.address = address;
        this.views = views;
        this.viewTime = viewTime;
    }

    public IPAddress() {
    }
}
