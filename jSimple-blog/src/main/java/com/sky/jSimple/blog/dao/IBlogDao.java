package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.data.DBHelper;
import com.sky.jSimple.data.SQLHelper;
import com.sky.jSimple.exception.JSimpleException;

public interface IBlogDao {
	void insert(Blog blog) throws JSimpleException;
   
   void update(Blog blog) throws JSimpleException;
   
   void delete(Long id) throws JSimpleException;
   
   Blog getById(Long id) throws JSimpleException;
   
   List<Blog> getPager(int pageNumber,int pageSize,String condition,String sort,Object ... params) throws JSimpleException;
   
   long getCount(String condition,Object ... params) throws JSimpleException;
   
   Blog getByLinkName(String linkName) throws JSimpleException;
  
	
	List<Blog> getAll(String sort) throws JSimpleException;
}
