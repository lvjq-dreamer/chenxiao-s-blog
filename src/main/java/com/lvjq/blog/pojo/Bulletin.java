package com.lvjq.blog.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@Table(name="t_bulletin")
public class Bulletin {

    @Id
    @GeneratedValue
    private Long id;

    private String harvest ;

    private String uncompleted;

}
