package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="t_message")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
