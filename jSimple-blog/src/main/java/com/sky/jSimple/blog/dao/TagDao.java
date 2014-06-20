package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.exception.JSimpleException;

@Bean
public class TagDao extends BaseDao<Tag> implements ITagDao {
   public Tag getByName(String name) throws JSimpleException
   {
		 return getjSimpleDataTemplate().querySingleByCondition(Tag.class,"name=?", name);
   }
   
   public Tag getByLinkName(String linkName) throws JSimpleException
   {
		 return getjSimpleDataTemplate().querySingleByCondition(Tag.class,"linkName=?", linkName);
   }
}
