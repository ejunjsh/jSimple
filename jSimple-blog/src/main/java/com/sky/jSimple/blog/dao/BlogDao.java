package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Blog;

@Bean
public class BlogDao extends BaseDao<Blog> implements IBlogDao {

    public Blog getByLinkName(String linkName) {
        return getjSimpleDataTemplate().querySingleByCondition(Blog.class, "linkName=?", linkName);
    }
}
