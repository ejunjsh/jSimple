package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ITagDao;
import com.sky.jSimple.blog.entity.Category;
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
		tagDao.insert(tag);
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

	public Tag getByLinkName(String linkName) throws JSimpleException {
		return tagDao.getByLinkName(linkName);
	}
	
	@Override
    public List<Tag> getAllTags(String sortBy,boolean isDesc) throws JSimpleException
    {
    	String sort=sortBy +" "+(isDesc?"desc":"asc");
    	return tagDao.getAll(sort);
    }

}
