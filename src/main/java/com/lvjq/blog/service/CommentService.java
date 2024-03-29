package com.lvjq.blog.service;


import com.lvjq.blog.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    List<Comment> listComment();

    Page<Comment> listCommentsPage(Pageable pageable);

}
