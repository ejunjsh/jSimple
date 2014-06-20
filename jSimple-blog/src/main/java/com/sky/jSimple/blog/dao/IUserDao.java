package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.blog.entity.User;
import com.sky.jSimple.exception.JSimpleException;

public interface IUserDao {
	void insert(User user) throws JSimpleException;
	   
	   void update(User user) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   User getById(Long id) throws JSimpleException;
	   
	   List<User> getPager(int pageNumber,int pageSize,String condition,String sort,Object ... params) throws JSimpleException;
	   
	   long getCount(String condition,Object ... params) throws JSimpleException;
	   
	   List<User> getAll(String sort) throws JSimpleException;
}
