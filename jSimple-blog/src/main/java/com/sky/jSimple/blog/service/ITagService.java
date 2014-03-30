package com.sky.jSimple.blog.service;

import java.util.List;

import com.sky.jSimple.blog.entity.Tag;

public interface ITagService {
	 void insert(Tag tag);
	   
	   void update(Tag tag);
	   
	   void delete(Long id);
	   
	   Tag getById(Long id);
	   
	   List<Tag> getPager(int pageNumber,int pageSize,String condition,String sort);
	   
	   long getCount(String condition);
}
