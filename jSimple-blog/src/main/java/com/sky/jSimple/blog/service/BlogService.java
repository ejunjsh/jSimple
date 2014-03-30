package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.IBlogDao;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class BlogService implements IBlogService {
	
	@Inject
	private IBlogDao blogDao;

	@Transactional
	public void insert(Blog blog) {
		blogDao.insert(blog);

	}

	@Transactional
	public void update(Blog blog) {
        blogDao.update(blog);
	}

	@Transactional
	public void delete(Long id) {
	    blogDao.delete(id);
	}

	public Blog getById(Long id) {
		return blogDao.getById(id);
	}

	public List<Blog> getPager(int pageNumber, int pageSize, String condition,
			String sort) {
		return blogDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) {
		return blogDao.getCount(condition);
	}

}
