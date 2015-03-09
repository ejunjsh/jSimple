package com.sky.jSimple.blog.interceptor;

import com.sky.jSimple.blog.annotation.ViewCount;
import com.sky.jSimple.blog.entity.Blog;
import com.sky.jSimple.blog.service.IBlogService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;
import com.sky.jSimple.mvc.annotation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Order(97)
public class ViewCountInterceptor extends Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(ViewCountInterceptor.class);
    public Map<String, Integer> viewCount = new ConcurrentHashMap<String, Integer>();

    @Inject
    private IBlogService blogService;


    public ViewCountInterceptor() {
        Timer timer = new Timer();
        //每十分钟执行一次,更新阅读数
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String key : viewCount.keySet()) {
                    int count = viewCount.get(key) == null ? 0 : viewCount.get(key);
                    if (count > 0) {
                        Blog blog = blogService.getByLinkName(key);
                        blogService.updateViewCount(blog.getId(), blog.getViewCount() + viewCount.get(key));
                        //清空
                        viewCount.put(key, 0);
                        logger.debug("blogViewCountUpdate-博客" + key + "更新" + count + "次阅读量.");
                    }
                }
            }
        }, 60000, 60000);
    }

    public Class<? extends Annotation> getAnnotation() {
        return ViewCount.class;
    }

    @Override
    public ActionResult before(Class<?> cls, Method method, Object[] params) {
        String linkName = params[0].toString();

        int count = viewCount.get(linkName) == null ? 0 : viewCount.get(linkName);
        count++;
        viewCount.put(linkName, count);
        return null;
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, ActionResult result) {

    }

    public IBlogService getBlogService() {
        return blogService;
    }

    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

}
