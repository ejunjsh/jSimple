package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Tag;

@Bean
public class TagDao extends BaseDao<Tag> implements ITagDao {
    public Tag getByName(String name) {
        return getjSimpleDataTemplate().querySingleByCondition(Tag.class, "name=?", name);
    }

    public Tag getByLinkName(String linkName) {
        return getjSimpleDataTemplate().querySingleByCondition(Tag.class, "linkName=?", linkName);
    }
}
