package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.IBlogDao;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class BlogService implements IBlogService {
	
	@Inject
	private IBlogDao blogDao;

	@Transactional
	public void insert(Blog blog) throws JSimpleException{
		long id=blogDao.insert(blog);
        blog.setId(id);
	}

	@Transactional
	public void update(Blog blog) throws JSimpleException{
        blogDao.update(blog);
	}

	@Transactional
	public void delete(Long id) throws JSimpleException {
	    blogDao.delete(id);
	}

	public Blog getById(Long id) throws JSimpleException{
		return blogDao.getById(id);
	}

	public List<Blog> getPager(int pageNumber, int pageSize, String condition,
			String sort) throws JSimpleException{
		return blogDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) throws JSimpleException{
		return blogDao.getCount(condition);
	}

}
