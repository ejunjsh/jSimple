package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Blog;

import java.util.List;

public interface IBlogDao {
    void insert(Blog blog);

    void update(Blog blog);

    void delete(Long id);

    Blog getById(Long id);

    List<Blog> getPager(int pageNumber, int pageSize, String condition, String sort, Object... params);

    long getCount(String condition, Object... params);

    Blog getByLinkName(String linkName);


    List<Blog> getAll(String sort);
}
