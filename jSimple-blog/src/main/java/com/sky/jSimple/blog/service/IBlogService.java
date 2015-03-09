package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.model.Pagination;

import java.util.List;

public interface IBlogService {
    void insert(Blog blog);

    void update(Blog blog);

    void delete(Long id);

    Blog getById(Long id);

    Pagination<Blog> getAll(int pageNumber, int pageSize);


    Blog getByLinkName(String linkName);

    Pagination<Blog> getByTagLinkName(int pageNumber, int pageSize, String linkName,
                                      String sortBy, boolean isDesc);

    List<Blog> getOrderByReadingCount();

    Pagination<Blog> getByCategoryLinkName(int pageNumber, int pageSize,
                                           String linkName, String sortBy, boolean isDesc);

    Pagination<Blog> getByRecommend(int pageNumber, int pageSize,
                                    String sortBy, boolean isDesc);

    Blog getNextBlog(long id);

    Blog getPrevBlog(long id);

    void updateViewCount(long id, long count);

    void updateRecommend(long id, int isRecommend);
}
