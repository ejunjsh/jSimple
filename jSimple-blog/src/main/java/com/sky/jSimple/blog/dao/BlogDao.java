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
		   String sql=SQLHelper.generateSelectSQL(Blog.class, "linkName=?", "");
			 return DBHelper.queryBean(Blog.class, sql, linkName);
	   }
	
	public List<Blog> getByCategoryId(int page,int pageSize,long categoryId,String sortBy,boolean isDesc) throws JSimpleException
	{
		   String sort=sortBy +" "+(isDesc?"desc":"asc");
		   String sql=SQLHelper.generateSelectSQLForPager(page, pageSize,Blog.class, "categoryId=?", sort);
		   return DBHelper.queryBeanList(Blog.class, sql, categoryId);
	}
	
	public long countByCategoryId(long categoryId) throws JSimpleException
	{
		   String sql=SQLHelper.generateSelectSQLForCount(Blog.class, "categoryId=?");
		   return DBHelper.queryCount( sql, categoryId);
	}
	
	public List<Blog> getByTagName(int page,int pageSize,String tagName,String sortBy,boolean isDesc) throws JSimpleException
	{
		   String sort=sortBy +" "+(isDesc?"desc":"asc");
		   String sql=SQLHelper.generateSelectSQLForPager(page, pageSize,Blog.class, "tags like CONCAT('%',?, '%')", sort);
		   return DBHelper.queryBeanList(Blog.class, sql, tagName);
	}
	
	public long countByTagName(String tagName) throws JSimpleException
	{
		   String sql=SQLHelper.generateSelectSQLForCount(Blog.class, "tags like CONCAT('%',?, '%')");
		   return DBHelper.queryCount( sql, tagName);
	}
}
