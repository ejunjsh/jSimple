package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.Default;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import com.sky.jSimple.mvc.annotation.HttpPut;

import java.util.Date;

@Bean
public class BlogController extends BaseController {

	@Inject
	private IBlogService blogService;
	
	@Default
	public ActionResult index()
	{
		return html("/app/admin.html");
	}
	
	@HttpPost("/api/blog")
	public ActionResult addBlog(Blog blog) throws JSimpleException
	{

		blog.setCreatedDate(new Date());
		blog.setLastModifiedDate(new Date());
		blog.setUid(1);
		blog.setViewCount(0);
		blogService.insert(blog);
	    
	    return json(blog);
	}
	
	@HttpPut("/api/blog")
	public ActionResult editBlog(Blog blog) throws JSimpleException
	{
        Blog b =blogService.getById(blog.getId());
        b.setCategoryId(blog.getCategoryId());
        b.setContent(blog.getContent());
        b.setLastModifiedDate(new Date());
        b.setLinkName(blog.getLinkName());
        b.setTags(blog.getTags());
        b.setTitle(blog.getTitle());
		blogService.update(b);
	    
	    return json(b);
	}
	
	@HttpGet("/api/blog/getAllBlog")
	public ActionResult getAllBlog(int p) throws JSimpleException
	{
		if(p==0)
		{
			p=1;
		}
		
		Pagination pagination=blogService.getAll(p,Pagination.PAGESIZE);
		pagination.setCurrentPage(p);
	    return json(pagination);
	}
	
	@HttpGet("/api/blog/{linkName}")
	public ActionResult getBlogByLinkName(String linkName) throws JSimpleException
	{
	     Blog blog= blogService.getByLinkName(linkName);
	     blog.setViewCount(blog.getViewCount()+1);
	     blogService.update(blog);
	     return json(blog);
	}
	
	@HttpGet("/api/blog/getBlogByCategoryLinkName")
	public ActionResult getBlogByCategoryLinkName(int p,String linkName) throws JSimpleException
	{
		if(p==0)
		{
			p=1;
		}
		
		Pagination pagination=blogService.getByCategoryLinkName(p,Pagination.PAGESIZE,linkName,"lastModifiedDate",true);
		pagination.setCurrentPage(p);
	    return json(pagination);
	}
	
	@HttpGet("/api/blog/getBlogByTagLinkName")
	public ActionResult getBlogByTagLinkName(int p,String linkName) throws JSimpleException
	{
		if(p==0)
		{
			p=1;
		}
		
		Pagination pagination=blogService.getByTagLinkName(p,Pagination.PAGESIZE,linkName,"lastModifiedDate",true);
		pagination.setCurrentPage(p);
	    return json(pagination);
	}

	public IBlogService getBlogService() {
		return blogService;
	}

	public void setBlogService(IBlogService blogService) {
		this.blogService = blogService;
	}
}
