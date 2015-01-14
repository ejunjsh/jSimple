package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Category;

import java.util.List;

;

public interface ICategoryService {
    void insert(Category category);

    void update(Category category);

    void delete(Long id);

    Category getById(Long id);


    Category getByLinkName(String linkName);

    List<Category> getAllCategories(String sortBy, boolean isDesc);


}
