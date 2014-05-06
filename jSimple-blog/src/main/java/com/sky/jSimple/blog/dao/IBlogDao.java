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
   
   List<Blog> getPager(int pageNumber,int pageSize,String condition,String sort) throws JSimpleException;
   
   long getCount(String condition) throws JSimpleException;
   
   Blog getByLinkName(String linkName) throws JSimpleException;
   
    List<Blog> getByCategoryId(int page,int pageSize,long categoryId,String sortBy,boolean isDesc) throws JSimpleException;

	long countByCategoryId(long categoryId) throws JSimpleException;
	
    List<Blog> getByTagName(int page,int pageSize,String tagName,String sortBy,boolean isDesc) throws JSimpleException;
	
	long countByTagName(String tagName) throws JSimpleException;
}
