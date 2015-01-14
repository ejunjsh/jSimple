package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Comment;
import com.sky.jSimple.blog.model.Pagination;

public interface ICommentService {
    void insert(Comment comment);

    void update(Comment comment);

    void delete(Long id);

    Comment getById(Long id);


    Pagination<Comment> getByBlogId(int pageNumber, int pageSize, long blogId,
                                    String sortBy, boolean isDesc);
}
