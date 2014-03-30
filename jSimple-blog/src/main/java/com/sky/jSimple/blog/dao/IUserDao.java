package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.blog.entity.User;

public interface IUserDao {
	 void insert(User user);
	   
	   void update(User user);
	   
	   void delete(Long id);
	   
	   User getById(Long id);
	   
	   List<User> getPager(int pageNumber,int pageSize,String condition,String sort);
	   
	   long getCount(String condition);
}
