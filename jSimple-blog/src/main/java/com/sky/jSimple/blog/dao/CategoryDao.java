package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.data.DBHelper;
import com.sky.jSimple.data.SQLHelper;
import com.sky.jSimple.exception.JSimpleException;

@Bean
public class CategoryDao extends BaseDao<Category> implements ICategoryDao {
	public Category getByLinkName(String linkName) throws JSimpleException
	   {
			 return getjSimpleDataTemplate().querySingleByCondition(Category.class,"linkName=?", linkName);
	   }
}
