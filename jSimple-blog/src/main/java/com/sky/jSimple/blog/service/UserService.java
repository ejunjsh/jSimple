package com.sky.jSimple.blog.service;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.IUserDao;
import com.sky.jSimple.blog.entity.User;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class UserService implements IUserService {

    @Inject
    private IUserDao userDao;

    @Transactional
    public void insert(User user) {
        userDao.insert(user);
    }

    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User Login(String account, String pwd) {
        String condition;
        //邮件登录
        if (account.contains("@")) {
            condition = "email=? and pwd=? ";
        } else {
            condition = "nickName=? and pwd=? ";
        }
        Object[] params = {account, pwd};
        return userDao.getByCondition(condition, params);
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
}
