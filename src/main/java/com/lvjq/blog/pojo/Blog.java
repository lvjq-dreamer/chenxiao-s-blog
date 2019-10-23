package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    private String firstPicture;

    private String flag;//原创，转载，翻译

    private Integer views;

    private boolean appreciation; //赞赏是否开启

    private boolean shareStatement;//版权声明

    private boolean commentabled; //评论是否开启

    private boolean published; //是否发布

    private boolean recommend;//是否推荐


    @Transient
    private String tagIds;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)  //将其转换为数据库的时间格式
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    //把tags转换成字符串
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag : tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag=true;
                }
                ids.append(tag.getId());

            }
            return ids.toString();
        }else{
            return  tagIds;
        }
    }
}
