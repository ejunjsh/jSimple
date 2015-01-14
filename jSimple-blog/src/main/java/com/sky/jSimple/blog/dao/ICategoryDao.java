package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Category;

import java.util.List;

public interface ICategoryDao {
    void insert(Category category);

    void update(Category category);

    void delete(Long id);

    Category getById(Long id);

    List<Category> getPager(int pageNumber, int pageSize, String condition, String sort, Object... params);

    long getCount(String condition, Object... params);

    Category getByLinkName(String linkName);

    List<Category> getAll(String sort);
}
