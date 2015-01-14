package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.User;

import java.util.List;

public interface IUserDao {
    void insert(User user);

    void update(User user);

    void delete(Long id);

    User getById(Long id);

    List<User> getPager(int pageNumber, int pageSize, String condition, String sort, Object... params);

    long getCount(String condition, Object... params);

    List<User> getAll(String sort);

    User getByCondition(String condition, Object... params);
}
