package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.User;

public interface IUserService {
    void insert(User user);

    void update(User user);

    void delete(Long id);

    User getById(Long id);

    User Login(String nickName, String pwd);
}
