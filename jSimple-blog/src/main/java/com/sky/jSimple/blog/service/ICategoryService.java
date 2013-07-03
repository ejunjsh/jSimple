package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.exception.JSimpleException;

import java.util.List;

public interface ICategoryService {
	 void insert(Category category) throws JSimpleException;
	   
	   void update(Category category) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Category getById(Long id) throws JSimpleException;
	   
	   
	   Category getByLinkName(String linkName) throws JSimpleException;

	List<Category> getAllCategories(String sortBy, boolean isDesc)
			throws JSimpleException;
	   
	   
}
