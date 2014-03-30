package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.blog.entity.Blog;

public interface IBlogService {
	 void insert(Blog blog);
	   
	   void update(Blog blog);
	   
	   void delete(Long id);
	   
	   Blog getById(Long id);
	   
	   List<Blog> getPager(int pageNumber,int pageSize,String condition,String sort);
	   
	   long getCount(String condition);
}
