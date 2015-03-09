package com.sky.jSimple.blog.dao;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Blog;

import java.util.ArrayList;
import java.util.List;

@Bean
public class BlogDao extends BaseDao<Blog> implements IBlogDao {

    public Blog getByLinkName(String linkName) {
        return getjSimpleDataTemplate().querySingleByCondition(Blog.class, "linkName=?", linkName);
    }

    @Override
    public Blog getNextBlog(long id) {
        String sql = "select * from blog where id>? order by id asc limit 1";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return getjSimpleDataTemplate().querySingle(Blog.class, sql, params.toArray());
    }

    @Override
    public Blog getPrevBlog(long id) {
        String sql = "select * from blog where id<? order by id desc limit 1";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return getjSimpleDataTemplate().querySingle(Blog.class, sql, params.toArray());
    }

    @Override
    public void updateViewCount(long id, long count) {
        String sql = "update blog set viewCount=? ,lastModifiedDate=now() where id=?";
        List<Object> params = new ArrayList<Object>();
        params.add(count);
        params.add(id);
        getjSimpleDataTemplate().execute(sql, params.toArray());
    }

    @Override
    public void updateRecommend(long id, int isRecommend) {
        String sql = "update blog set isRecommend=? ,lastModifiedDate=now() where id=?";
        List<Object> params = new ArrayList<Object>();
        params.add(isRecommend);
        params.add(id);
        getjSimpleDataTemplate().execute(sql, params.toArray());
    }

}
