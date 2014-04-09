package com.sky.jSimple.blog.controller;

import java.util.Date;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.service.ICategoryService;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.BaseController;
import com.sky.jSimple.mvc.JsonResult;
import com.sky.jSimple.mvc.annotation.HttpPost;

@Bean
public class CategoryController extends BaseController {

	@Inject
	private ICategoryService categoryService;
	
	@HttpPost("/api/category")
	public ActionResult tagIndex(Category category) throws JSimpleException
	{
	    if(category.getId()==0)
	    {
	    	category.setCreatedDate(new Date());
	    	category.setLastModifiedDate(new Date());
	    	category.setUid(1);
	    	categoryService.insert(category);
	    }
	    else {
	    	category.setLastModifiedDate(new Date());
			categoryService.update(category);
		}
	    return json(category);
	}
}
