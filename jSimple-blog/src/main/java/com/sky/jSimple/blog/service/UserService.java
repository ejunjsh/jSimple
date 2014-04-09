package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.IUserDao;
import com.sky.jSimple.blog.entity.User;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class UserService implements IUserService {

	@Inject
	private IUserDao userDao;
	
	@Transactional
	public void insert(User user) throws JSimpleException{
		long id=userDao.insert(user);
		user.setId(id);

	}

	@Transactional
	public void update(User user) throws JSimpleException{
		userDao.update(user);
	}

	@Transactional
	public void delete(Long id) throws JSimpleException{
		userDao.delete(id);
	}

	public User getById(Long id)throws JSimpleException {
		return userDao.getById(id);
	}

	public List<User> getPager(int pageNumber, int pageSize, String condition,
			String sort)throws JSimpleException{
		return userDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) throws JSimpleException{
		return userDao.getCount(condition);
	}

}
