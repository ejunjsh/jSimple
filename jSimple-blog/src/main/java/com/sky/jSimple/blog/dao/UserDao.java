package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.User;

@Bean
public class UserDao extends BaseDao<User> implements IUserDao {

    @Override
    public User getByCondition(String condition, Object... params) {
        return getjSimpleDataTemplate().querySingleByCondition(User.class, condition, params);
    }
}
