package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.exception.JSimpleException;

public interface IBlogService {
	 void insert(Blog blog) throws JSimpleException;
	   
	   void update(Blog blog) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Blog getById(Long id) throws JSimpleException;
	   
	   Pagination getAll(int pageNumber,int pageSize)throws JSimpleException;
	   
	   
	   Blog getByLinkName(String linkName) throws JSimpleException;

	Pagination getByTagLinkName(int pageNumber, int pageSize, String linkName,
			String sortBy, boolean isDesc) throws JSimpleException;

	Pagination getByCategoryLinkName(int pageNumber, int pageSize,
			String linkName, String sortBy, boolean isDesc)
			throws JSimpleException;
}
