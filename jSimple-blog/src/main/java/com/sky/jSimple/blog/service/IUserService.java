package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.User;
import com.sky.jSimple.exception.JSimpleException;

public interface IUserService {
	 void insert(User user) throws JSimpleException;
	   
	   void update(User user) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   User getById(Long id) throws JSimpleException;
}
