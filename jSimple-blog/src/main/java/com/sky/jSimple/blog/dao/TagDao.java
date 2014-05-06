package com.sky.jSimple.blog.dao;

import java.util.List;
import java.util.ListIterator;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.data.DBHelper;
import com.sky.jSimple.data.SQLHelper;
import com.sky.jSimple.exception.JSimpleException;

@Bean
public class TagDao extends BaseDao<Tag> implements ITagDao {
   public Tag getByName(String name) throws JSimpleException
   {
	   String sql=SQLHelper.generateSelectSQL(Tag.class, "name=?", "");
		 return DBHelper.queryBean(Tag.class, sql, name);
   }
   
   public Tag getByLinkName(String linkName) throws JSimpleException
   {
	   String sql=SQLHelper.generateSelectSQL(Tag.class, "linkName=?", "");
		 return DBHelper.queryBean(Tag.class, sql, linkName);
   }
}
