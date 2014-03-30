package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ITagDao;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

@Bean
public class TagService implements ITagService {

	@Inject
	private ITagDao tagDao;
	
	@Transactional
	public void insert(Tag tag) {
		tagDao.insert(tag);
	}

	@Transactional
	public void update(Tag tag) {
		tagDao.update(tag);
	}

	@Transactional
	public void delete(Long id) {
		tagDao.delete(id);
	}

	public Tag getById(Long id) {
		return tagDao.getById(id);
	}

	public List<Tag> getPager(int pageNumber, int pageSize, String condition,
			String sort) {
		return tagDao.getPager(pageNumber, pageSize, condition, sort);
	}

	public long getCount(String condition) {
		return tagDao.getCount(condition);
	}

}
