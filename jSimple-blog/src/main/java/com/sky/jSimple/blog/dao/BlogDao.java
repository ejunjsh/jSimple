package com.sky.jSimple.blog.dao;

import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.data.DBHelper;
import com.sky.jSimple.data.SQLHelper;
import com.sky.jSimple.exception.JSimpleException;

@Bean
public class BlogDao extends BaseDao<Blog> implements IBlogDao {

	public Blog getByLinkName(String linkName) throws JSimpleException
	   {
		   return getjSimpleDataTemplate().querySingleByCondition(Blog.class,"linkName=?",  linkName);
	   }
}
