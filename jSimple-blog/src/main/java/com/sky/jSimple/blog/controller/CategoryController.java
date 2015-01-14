package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.service.ICategoryService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import com.sky.jSimple.mvc.annotation.HttpPut;

import java.util.Date;
import java.util.List;

@Bean
public class CategoryController extends BaseController {

    @Inject
    private ICategoryService categoryService;


    @HttpPost("/api/category")
    public ActionResult addCategory(Category category) {

        category.setCreatedDate(new Date());
        category.setLastModifiedDate(new Date());
        category.setUid(1);
        categoryService.insert(category);

        return json(category);
    }

    @HttpGet("/api/category")
    public ActionResult getCategories(Category category) {
        List<Category> categories = categoryService.getAllCategories("name", true);
        return json(categories);
    }

    @HttpGet("/api/category/{linkName}")
    public ActionResult getCategoryByLinkName(String linkName) {
        Category category = categoryService.getByLinkName(linkName);
        return json(category);
    }

    @HttpPut("/api/category")
    public ActionResult updateCategory(Category category) {
        Category c = categoryService.getById(category.getId());
        c.setLinkName(category.getLinkName());
        c.setName(category.getName());
        c.setLastModifiedDate(new Date());
        categoryService.update(c);
        return json(category);
    }

    public ICategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }


}
