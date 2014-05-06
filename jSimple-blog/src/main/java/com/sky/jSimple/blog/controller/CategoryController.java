package com.sky.jSimple.blog.controller;

import java.util.Date;
import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.blog.service.ICategoryService;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.ControllerBase;
import com.sky.jSimple.mvc.JsonResult;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import com.sky.jSimple.mvc.annotation.HttpPut;

@Bean
public class CategoryController extends BaseController {

	@Inject
	private ICategoryService categoryService;
	
	
	@HttpPost("/api/category")
	public ActionResult addCategory(Category category) throws JSimpleException
	{

	    category.setCreatedDate(new Date());
	    category.setLastModifiedDate(new Date());
	    category.setUid(1);
	    categoryService.insert(category);
	    
	    return json(category);
	}
	
	@HttpGet("/api/category")
	public ActionResult getCategories(Category category) throws JSimpleException
	{
	     List<Category> categories= categoryService.getPager(1,1000, null, "name");
	     return json(categories);
	}
	
	@HttpGet("/api/category/{linkName}")
	public ActionResult getCategoryByLinkName(String linkName) throws JSimpleException
	{
		Category category=categoryService.getByLinkName(linkName);
	     return json(category);
	}
	@HttpPut("/api/category")
	public ActionResult updateCategory(Category category) throws JSimpleException
	{
		Category c=categoryService.getById(category.getId());
		c.setLinkName(category.getLinkName());
		c.setName(category.getName());
		c.setLastModifiedDate(new Date());
		categoryService.update(c);
		return json(category);
	}
	

}
