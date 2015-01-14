package com.sky.jSimple.blog.service;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ICategoryDao;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;

import java.util.List;

@Bean
public class CategoryService implements ICategoryService {

    @Inject
    private ICategoryDao categoryDao;

    @Transactional
    public void insert(Category category) throws JSimpleException {
        categoryDao.insert(category);
    }

    @Transactional
    public void update(Category category) throws JSimpleException {
        categoryDao.update(category);
    }

    @Transactional
    public void delete(Long id) throws JSimpleException {
        categoryDao.delete(id);
    }

    public Category getById(Long id) {
        return categoryDao.getById(id);
    }

    public Category getByLinkName(String linkName) throws JSimpleException {
        return categoryDao.getByLinkName(linkName);
    }

    @Override
    public List<Category> getAllCategories(String sortBy, boolean isDesc) throws JSimpleException {
        String sort = sortBy + " " + (isDesc ? "desc" : "asc");
        return categoryDao.getAll(sort);
    }

    public ICategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

}
