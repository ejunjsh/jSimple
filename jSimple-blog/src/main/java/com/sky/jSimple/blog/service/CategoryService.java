package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ICategoryDao;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class CategoryService implements ICategoryService {
	
	@Inject
	private ICategoryDao categoryDao;

	@Transactional
	public void insert(Category category) throws JSimpleException{
		long id=categoryDao.insert(category);
		category.setId(id);
	}

	@Transactional
	public void update(Category category) throws JSimpleException{
		categoryDao.update(category);
	}

	@Transactional
	public void delete(Long id) throws JSimpleException{
		categoryDao.delete(id);
	}

	public Category getById(Long id) throws JSimpleException{
		return categoryDao.getById(id);
	}

	public List<Category> getPager(int pageNumber, int pageSize,
			String condition, String sort) throws JSimpleException{
		return categoryDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) throws JSimpleException{
		return categoryDao.getCount(condition);
	}

}
