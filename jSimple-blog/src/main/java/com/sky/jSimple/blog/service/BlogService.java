package com.sky.jSimple.blog.service;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.IBlogDao;
import com.sky.jSimple.blog.dao.ICategoryDao;
import com.sky.jSimple.blog.dao.ITagDao;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.cache.annotation.Evict;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

import java.util.Date;
import java.util.List;

@Bean
public class BlogService implements IBlogService {

    @Inject
    private IBlogDao blogDao;

    @Inject
    private ITagDao tagDao;

    @Inject
    private ICategoryDao categoryDao;

    private void updateTags(String tags) {
        String[] strings = tags.split(" ");
        for (String s : strings) {
            if (s != null && !s.isEmpty()) {
                Tag tag = tagDao.getByName(s);
                if (tag == null) {
                    tag = new Tag();
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
    @Evict(scope = "tag1")
    public void insert(Blog blog) {
        blogDao.insert(blog);

        updateTags(blog.getTags());
    }

    @Transactional
    @Evict(scope = "tag1")
    public void update(Blog blog) {
        blogDao.update(blog);

        updateTags(blog.getTags());
    }

    @Transactional
    public void delete(Long id) {
        blogDao.delete(id);
    }

    public Blog getById(Long id) {
        return blogDao.getById(id);
    }

    public Blog getByLinkName(String linkName) {
        return blogDao.getByLinkName(linkName);
    }

    @Override
    public Pagination<Blog> getAll(int pageNumber, int pageSize) {
        Pagination pagination = new Pagination();
        pagination.setData(blogDao.getPager(pageNumber, pageSize, "", "createdDate desc"));
        pagination.setRecordCount(blogDao.getCount(""));
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNumber);
        return pagination;
    }

    @Override
    public List<Blog> getOrderByReadingCount() {
        return blogDao.getPager(1, 10, "", "viewCount desc");
    }


    @Override
    public Pagination<Blog> getByCategoryLinkName(int pageNumber, int pageSize, String linkName, String sortBy,
                                                  boolean isDesc) {
        Category category = categoryDao.getByLinkName(linkName);
        if (category != null) {
            Pagination pagination = new Pagination();

            String sort = sortBy + " " + (isDesc ? "desc" : "asc");

            pagination.setData(blogDao.getPager(pageNumber, pageSize, "categoryId=?", sort, category.getId()));
            pagination.setRecordCount(blogDao.getCount("categoryId=?", category.getId()));
            pagination.setPageSize(pageSize);
            pagination.setCurrentPage(pageNumber);
            return pagination;
        }
        return null;
    }

    @Override
    public Pagination<Blog> getByTagLinkName(int pageNumber, int pageSize, String linkName, String sortBy, boolean isDesc) {
        Tag tag = tagDao.getByLinkName(linkName);
        if (tag != null) {
            Pagination pagination = new Pagination();

            String sort = sortBy + " " + (isDesc ? "desc" : "asc");
            pagination.setData(blogDao.getPager(pageNumber, pageSize, "tags like CONCAT('%',?, '%')", sort, tag.getName()));
            pagination.setPageSize(pageSize);
            pagination.setRecordCount(blogDao.getCount("tags like CONCAT('%',?, '%')", tag.getName()));
            pagination.setCurrentPage(pageNumber);
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
