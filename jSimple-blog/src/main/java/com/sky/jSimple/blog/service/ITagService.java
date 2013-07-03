package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.exception.JSimpleException;

import java.util.List;

public interface ITagService {
	 void insert(Tag tag) throws JSimpleException;
	   
	   void update(Tag tag) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Tag getById(Long id) throws JSimpleException;
	   
	   
	   Tag getByLinkName(String linkName) throws JSimpleException;

	List<Tag> getAllTags(String sortBy, boolean isDesc) throws JSimpleException;
}
