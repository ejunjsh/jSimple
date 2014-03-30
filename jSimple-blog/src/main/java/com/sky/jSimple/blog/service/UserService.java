package com.sky.jSimple.blog.service;

import java.util.List;

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

	public List<User> getPager(int pageNumber, int pageSize, String condition,
			String sort) {
		return userDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) {
		return userDao.getCount(condition);
	}

}
