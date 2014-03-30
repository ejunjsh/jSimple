package com.sky.jSimple.blog.controller;

import java.util.Map;

import org.codehaus.jackson.impl.JsonReadContext;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.annotation.Authority;
import com.sky.jSimple.blog.model.BlogModel;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.BaseController;
import com.sky.jSimple.mvc.FileResult;
import com.sky.jSimple.mvc.FreemarkerResult;
import com.sky.jSimple.mvc.HtmlResult;
import com.sky.jSimple.mvc.JsonResult;
import com.sky.jSimple.mvc.JspResult;
import com.sky.jSimple.mvc.VelocityResult;
import com.sky.jSimple.mvc.annotation.HttpGet;

@Bean
public class BlogController extends BaseController {

	@Inject
	private IBlogService blogService;
	
	@HttpGet("/blog/{id}")
	public ActionResult blogById(Map<String, Object> model)
	{
		blogService.delete((long) 1);
		return new JspResult("/WEB-INF/jsp/index.jsp", model);
	}
	
	@HttpGet("/blog/ftl/{id}")
	@Authority("123")
	public ActionResult blogftl(Map<String, Object> model)
	{
		BlogModel blogModel=new BlogModel();
		blogModel.setId(123);
		model.put("blog", blogModel);
		return new FreemarkerResult("/WEB-INF/ftl/blog.ftl", model);
	}
	
	@HttpGet("/blog/vm/{id}")
	@Authority("123")
	public ActionResult blogvm(Map<String, Object> model)
	{
		BlogModel blogModel=new BlogModel();
		blogModel.setId(123);
		model.put("blog", blogModel);
		return new VelocityResult("/WEB-INF/vm/blog.vm", model);
	}
	
	@HttpGet("/blog/json/{id}")
	@Authority("123")
	public ActionResult blogJson(Map<String, Object> model)
	{
		BlogModel blogModel=new BlogModel();
		blogModel.setId(123);
		model.put("blog", blogModel);
		return new JsonResult(model);
	}
	
	@HttpGet("/blog/file/{id}")
	@Authority("123")
	public ActionResult blogFile(Map<String, Object> model)
	{
		return new FileResult("/WEB-INF/vm/blog.vm");
	}
	
	@HttpGet("/blog/edit")
	public HtmlResult editBlog(int pid,int id)
	{
		return new HtmlResult("you are editing.");
	}
}
