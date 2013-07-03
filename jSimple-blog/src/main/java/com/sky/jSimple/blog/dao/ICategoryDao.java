package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.exception.JSimpleException;

import java.util.List;

public interface ICategoryDao {
	void insert(Category category) throws JSimpleException;
	   
	   void update(Category category) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Category getById(Long id) throws JSimpleException;
	   
	   List<Category> getPager(int pageNumber,int pageSize,String condition,String sort,Object ... params) throws JSimpleException;
	   
	   long getCount(String condition,Object ... params) throws JSimpleException;
	   
	   Category getByLinkName(String linkName) throws JSimpleException;
	   
	   List<Category> getAll(String sort) throws JSimpleException;
}
