package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.annotation.Authority;
import com.sky.jSimple.blog.service.ITagService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.BaseController;
import com.sky.jSimple.mvc.TextResult;
import com.sky.jSimple.mvc.annotation.HttpGet;

@Bean
public class TagController extends BaseController {

	@Inject
	private ITagService tagService;
	
	@HttpGet("/tag/{id}")
	public ActionResult tagIndex(int id)
	{
		return new TextResult(""+id);
	}
}
