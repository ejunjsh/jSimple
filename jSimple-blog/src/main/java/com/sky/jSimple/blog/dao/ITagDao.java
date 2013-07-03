package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.exception.JSimpleException;

import java.util.List;

public interface ITagDao {
	void insert(Tag tag) throws JSimpleException;
	   
	   void update(Tag tag) throws JSimpleException;
	   
	   void delete(Long id) throws JSimpleException;
	   
	   Tag getById(Long id) throws JSimpleException;
	   
	   List<Tag> getPager(int pageNumber,int pageSize,String condition,String sort,Object ... params) throws JSimpleException;
	   
	   long getCount(String condition,Object ... params)throws JSimpleException ;
	   
	   Tag getByName(String name) throws JSimpleException;
	   
	   Tag getByLinkName(String linkName) throws JSimpleException;
	   
	   List<Tag> getAll(String sort) throws JSimpleException;
}
