package com.sky.jSimple.blog.service;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.IBlogDao;
import com.sky.jSimple.blog.dao.ICategoryDao;
import com.sky.jSimple.blog.dao.ITagDao;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;

import java.util.Date;

@Bean
public class BlogService implements IBlogService {
	
	@Inject
	private IBlogDao blogDao;
	
	@Inject
	private ITagDao tagDao;
	
	@Inject
	private ICategoryDao categoryDao;
	
	private void updateTags(String tags) throws JSimpleException
	{
		String[] strings=tags.split(" ");
		for(String s:strings)
		{
			if(s!=null&&!s.isEmpty())
			{
				Tag tag=tagDao.getByName(s);
				if(tag==null)
				{
					tag=new Tag();
					tag.setCreatedDate(new Date());
					tag.setLastModifiedDate(new Date());
					tag.setLinkName(s);
					tag.setName(s);
					tag.setUid(1);
					tagDao.insert(tag);
				}
			}
		}
	}

	@Transactional
	public void insert(Blog blog) throws JSimpleException{
		blogDao.insert(blog);
		
		updateTags(blog.getTags());
	}

	@Transactional
	public void update(Blog blog) throws JSimpleException{
        blogDao.update(blog);
        
        updateTags(blog.getTags());
	}

	@Transactional
	public void delete(Long id) throws JSimpleException {
	    blogDao.delete(id);
	}

	public Blog getById(Long id) throws JSimpleException{
		return blogDao.getById(id);
	}
	
	public Blog getByLinkName(String linkName) throws JSimpleException{
		return blogDao.getByLinkName(linkName);
	}

	public Pagination getAll(int pageNumber, int pageSize) throws JSimpleException{
		 Pagination pagination=new Pagination();
		 pagination.setData(blogDao.getPager(pageNumber, pageSize, "", "lastModifiedDate"));
		 pagination.setRecordCount(blogDao.getCount(""));
		 return pagination;
	}


	@Override
	public Pagination getByCategoryLinkName(int pageNumber, int pageSize,String linkName, String sortBy,
			boolean isDesc) throws JSimpleException {
		Category category=categoryDao.getByLinkName(linkName);
		if(category!=null)
		{
		 Pagination pagination=new Pagination();
		 
		 String sort=sortBy +" "+(isDesc?"desc":"asc");
		 
		pagination.setData(blogDao.getPager(pageNumber,pageSize,"categoryId=?",sort,category.getId()));
		pagination.setRecordCount(blogDao.getCount("categoryId=?",category.getId()));
		return pagination;
		}
		return null;
	}

	@Override
	public Pagination getByTagLinkName(int pageNumber, int pageSize,String linkName, String sortBy, boolean isDesc)
			throws JSimpleException {
		Tag tag=tagDao.getByLinkName(linkName);
		if(tag!=null)
		{
			Pagination pagination=new Pagination();
			
			String sort=sortBy +" "+(isDesc?"desc":"asc");
		    pagination.setData(blogDao.getPager(pageNumber,pageSize,"tags like CONCAT('%',?, '%')",sort, tag.getName()));
		    
		    pagination.setRecordCount(blogDao.getCount("tags like CONCAT('%',?, '%')", tag.getName()));
		    return pagination;
		}
		return null;
		
	}

	public IBlogDao getBlogDao() {
		return blogDao;
	}

	public void setBlogDao(IBlogDao blogDao) {
		this.blogDao = blogDao;
	}

	public ITagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(ITagDao tagDao) {
		this.tagDao = tagDao;
	}

	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

}
