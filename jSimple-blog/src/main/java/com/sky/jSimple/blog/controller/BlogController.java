package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.blog.service.ICategoryService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import com.sky.jSimple.mvc.annotation.HttpPut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Bean
public class BlogController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
    public Map<String, Integer> viewCount = new ConcurrentHashMap<String, Integer>();
    @Inject
    private IBlogService blogService;
    @Inject
    private ICategoryService categoryService;

    public BlogController() {
        Timer timer = new Timer();
        //每十分钟执行一次,更新阅读数
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String key : viewCount.keySet()) {
                    int count = viewCount.get(key) == null ? 0 : viewCount.get(key);
                    if (count > 0) {
                        Blog blog = blogService.getByLinkName(key);
                        blog.setViewCount(blog.getViewCount() + viewCount.get(key));
                        blogService.update(blog);
                        //清空
                        viewCount.put(key, 0);
                        logger.debug("blogViewCountUpdate-博客" + key + "更新" + count + "次阅读量.");
                    }
                }
            }
        }, 600000, 600000);
    }

    /**
     * 前台首页
     *
     * @return
     */
    @HttpGet("/?")
    public ActionResult index(int page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page == 0) {
            page = 1;
        }
        Pagination<Blog> blogs = blogService.getAll(page, Pagination.PAGESIZE);
        map.put("blogs", blogs);

        List<Category> categories = categoryService.getAllCategories("name", true);
        map.put("categories", categories);
        map.put("curCate", null);

        List<Blog> topViewCountBlogs = blogService.getOrderByReadingCount();
        map.put("topViewCountBlogs", topViewCountBlogs);
        return jsp("/WEB-INF/jsp/index.jsp", map);
    }

    @HttpGet("/blog/category/{categoryName}")
    public ActionResult getBlogByCategory(String categoryName, int page) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page == 0) {
            page = 1;
        }
        Pagination<Blog> blogs = blogService.getByCategoryLinkName(page, Pagination.PAGESIZE, categoryName, "createdDate", true);
        map.put("blogs", blogs);

        List<Category> categories = categoryService.getAllCategories("name", true);
        Category curCate = categoryService.getByLinkName(categoryName);
        map.put("categories", categories);
        map.put("curCate", curCate);

        List<Blog> topViewCountBlogs = blogService.getOrderByReadingCount();
        map.put("topViewCountBlogs", topViewCountBlogs);

        return jsp("/WEB-INF/jsp/index.jsp", map);
    }

    @HttpGet("/blog/{linkName}")
    public ActionResult getBlogDetail(String linkName) {
        Map<String, Object> map = new HashMap<String, Object>();
        Blog blog = blogService.getByLinkName(linkName);
        int count = viewCount.get(linkName) == null ? 0 : viewCount.get(linkName);
        count++;
        viewCount.put(linkName, count);
        map.put("blog", blog);

        List<Category> categories = categoryService.getAllCategories("name", true);
        map.put("categories", categories);

        map.put("curCate", blog.getCategory());

        List<Blog> topViewCountBlogs = blogService.getOrderByReadingCount();
        map.put("topViewCountBlogs", topViewCountBlogs);
        return jsp("/WEB-INF/jsp/blog/detail.jsp", map);
    }




    @HttpPost("/api/blog")
    public ActionResult addBlog(Blog blog)  {

        blog.setCreatedDate(new Date());
        blog.setLastModifiedDate(new Date());
        blog.setUid(1);
        blog.setViewCount(0);
        blogService.insert(blog);

        return json(blog);
    }

    @HttpPut("/api/blog")
    public ActionResult editBlog(Blog blog)  {
        Blog b = blogService.getById(blog.getId());
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
    public ActionResult getAllBlog(int p) {
        if (p == 0) {
            p = 1;
        }

        Pagination pagination = blogService.getAll(p, Pagination.PAGESIZE);
        return json(pagination);
    }

    @HttpGet("/api/blog/{linkName}")
    public ActionResult getBlogByLinkName(String linkName) {
        Blog blog = blogService.getByLinkName(linkName);
        return json(blog);
    }

    @HttpGet("/api/blog/getBlogByCategoryLinkName")
    public ActionResult getBlogByCategoryLinkName(int p, String linkName) {
        if (p == 0) {
            p = 1;
        }

        Pagination pagination = blogService.getByCategoryLinkName(p, Pagination.PAGESIZE, linkName, "createdDate", true);
        return json(pagination);
    }

    @HttpGet("/api/blog/getBlogByTagLinkName")
    public ActionResult getBlogByTagLinkName(int p, String linkName) {
        if (p == 0) {
            p = 1;
        }

        Pagination pagination = blogService.getByTagLinkName(p, Pagination.PAGESIZE, linkName, "createdDate", true);
        return json(pagination);
    }


    public IBlogService getBlogService() {
        return blogService;
    }

    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    public ICategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
