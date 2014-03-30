package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.blog.entity.Category;

public interface ICategoryDao {
	 void insert(Category category);
	   
	   void update(Category category);
	   
	   void delete(Long id);
	   
	   Category getById(Long id);
	   
	   List<Category> getPager(int pageNumber,int pageSize,String condition,String sort);
	   
	   long getCount(String condition);
}
