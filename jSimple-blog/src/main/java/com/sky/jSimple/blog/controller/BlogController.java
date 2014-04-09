package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.BaseController;
import com.sky.jSimple.mvc.TextResult;
import com.sky.jSimple.mvc.htmlResult;
import com.sky.jSimple.mvc.annotation.Default;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.Param;

@Bean
public class BlogController extends BaseController {

	@Inject
	private IBlogService blogService;
	
	@Default
	public ActionResult index()
	{
		return html("/app/admin.html");
	}
	
	@HttpGet("/blog/{id}/{fid}.json")
	public ActionResult blogIndex(@Param("id")int fffid,@Param("fid")int id)
	{
		return text(id+"   "+fffid);
	}
}
