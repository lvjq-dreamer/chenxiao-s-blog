package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;

    private String username;

    private String password;

    private String email;

    private Integer type;

    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();

}
