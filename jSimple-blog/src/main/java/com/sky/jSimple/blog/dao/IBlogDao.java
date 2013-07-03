package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.exception.JSimpleException;

import java.util.List;

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
