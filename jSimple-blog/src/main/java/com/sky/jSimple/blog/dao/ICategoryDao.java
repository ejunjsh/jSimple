package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.exception.JSimpleException;

public interface ICategoryDao {
	void insert(Category category) throws JSimpleException;
	   
	   void update(Category category) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Category getById(Long id) throws JSimpleException;
	   
	   List<Category> getPager(int pageNumber,int pageSize,String condition,String sort) throws JSimpleException;
	   
	   long getCount(String condition) throws JSimpleException;
	   
	   Category getByLinkName(String linkName) throws JSimpleException;
}
