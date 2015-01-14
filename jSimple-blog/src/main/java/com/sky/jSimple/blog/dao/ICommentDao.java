package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Comment;

import java.util.List;

/**
 * Created by shaojunjie on 2015/1/12.
 */
public interface ICommentDao {

    void insert(Comment comment);

    void update(Comment comment);

    void delete(Long id);

    Comment getById(Long id);

    List<Comment> getPager(int pageNumber, int pageSize, String condition, String sort, Object... params);

    long getCount(String condition, Object... params);
}
