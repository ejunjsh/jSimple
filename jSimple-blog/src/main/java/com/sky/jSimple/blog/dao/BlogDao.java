package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.exception.JSimpleException;

@Bean
public class BlogDao extends BaseDao<Blog> implements IBlogDao {

	public Blog getByLinkName(String linkName) throws JSimpleException
	   {
		   return getjSimpleDataTemplate().querySingleByCondition(Blog.class,"linkName=?",  linkName);
	   }
}
