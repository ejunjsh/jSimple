package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.exception.JSimpleException;

public interface IBlogService {
	 void insert(Blog blog) throws JSimpleException;
	   
	   void update(Blog blog) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Blog getById(Long id) throws JSimpleException;
	   
	   List<Blog> getPager(int pageNumber,int pageSize,String condition,String sort)throws JSimpleException;
	   
	   long getCount(String condition) throws JSimpleException;
}
