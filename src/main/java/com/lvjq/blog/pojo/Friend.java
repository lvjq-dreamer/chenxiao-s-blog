package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="t_friend")
public class Friend {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;

    private String occupation;

    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String avatar;

    private String flag;//大佬，朋友，网站

    private String website;
}
