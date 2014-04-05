package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.BaseController;
import com.sky.jSimple.mvc.TextResult;
import com.sky.jSimple.mvc.htmlResult;
import com.sky.jSimple.mvc.annotation.DefaultAction;
import com.sky.jSimple.mvc.annotation.HttpGet;

@Bean
public class BlogController extends BaseController {

	@Inject
	private IBlogService blogService;
	
	@DefaultAction
	public ActionResult index()
	{
		return new htmlResult("/app/admin.html");
	}
	
	@HttpGet("/blog/{id}")
	public ActionResult blogIndex(int id)
	{
		return new TextResult(""+id);
	}
}
