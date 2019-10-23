package com.lvjq.blog.dao;


import com.lvjq.blog.pojo.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletinRepository extends JpaRepository<Bulletin,Long> {

}
