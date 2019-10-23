package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar; //评论者的头像

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    //判断是否是博主的回复
    private boolean adminComment;

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", parentComment=" + parentComment +
                ", replyComments=" + replyComments +
                '}';
    }

}
