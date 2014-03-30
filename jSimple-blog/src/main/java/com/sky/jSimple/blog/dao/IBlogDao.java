package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.blog.entity.Blog;

public interface IBlogDao {
   void insert(Blog blog);
   
   void update(Blog blog);
   
   void delete(Long id);
   
   Blog getById(Long id);
   
   List<Blog> getPager(int pageNumber,int pageSize,String condition,String sort);
   
   long getCount(String condition);
}
