package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ICategoryDao;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class CategoryService implements ICategoryService {
	
	@Inject
	private ICategoryDao categoryDao;

	@Transactional
	public void insert(Category category) {
		categoryDao.insert(category);
	}

	@Transactional
	public void update(Category category) {
		categoryDao.update(category);
	}

	@Transactional
	public void delete(Long id) {
		categoryDao.delete(id);
	}

	public Category getById(Long id) {
		return categoryDao.getById(id);
	}

	public List<Category> getPager(int pageNumber, int pageSize,
			String condition, String sort) {
		return categoryDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) {
		return categoryDao.getCount(condition);
	}

}
