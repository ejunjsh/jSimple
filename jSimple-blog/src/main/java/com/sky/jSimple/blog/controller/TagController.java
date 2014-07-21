package com.sky.jSimple.blog.controller;

import java.util.Date;
import java.util.List;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.blog.service.ITagService;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPut;

@Bean
public class TagController extends BaseController {

	@Inject
	private ITagService tagService;
	
	
	@HttpGet("/api/tag")
	public ActionResult getTags() throws JSimpleException
	{
	     List<Tag> tags= tagService.getAllTags("name",true);
	     return json(tags);
	}
	
	@HttpGet("/api/tag/{linkName}")
	public ActionResult getTagByLinkName(String linkName) throws JSimpleException
	{
	     Tag tag=tagService.getByLinkName(linkName);
	     return json(tag);
	}
	@HttpPut("/api/tag")
	public ActionResult updateTag(Tag tag) throws JSimpleException
	{
		Tag t=tagService.getById(tag.getId());
		t.setLinkName(tag.getLinkName());
		t.setLastModifiedDate(new Date());
		tagService.update(t);
		return json(tag);
	}
	public ITagService getTagService() {
		return tagService;
	}
	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
	

}
