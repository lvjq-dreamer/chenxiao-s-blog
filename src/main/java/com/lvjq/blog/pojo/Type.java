package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

}
