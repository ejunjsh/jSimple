package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Category;

@Bean
public class CategoryDao extends BaseDao<Category> implements ICategoryDao {
    public Category getByLinkName(String linkName) {
        return getjSimpleDataTemplate().querySingleByCondition(Category.class, "linkName=?", linkName);
    }
}
