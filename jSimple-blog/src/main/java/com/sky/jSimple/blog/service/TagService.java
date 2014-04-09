package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ITagDao;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class TagService implements ITagService {

	@Inject
	private ITagDao tagDao;
	
	@Transactional
	public void insert(Tag tag) throws JSimpleException{
		long id=tagDao.insert(tag);
		tag.setId(id);
	}

	@Transactional
	public void update(Tag tag)  throws JSimpleException{
		tagDao.update(tag);
	}

	@Transactional
	public void delete(Long id) throws JSimpleException {
		tagDao.delete(id);
	}

	public Tag getById(Long id) throws JSimpleException {
		return tagDao.getById(id);
	}

	public List<Tag> getPager(int pageNumber, int pageSize, String condition,
			String sort) throws JSimpleException {
		return tagDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) throws JSimpleException {
		return tagDao.getCount(condition);
	}

}
