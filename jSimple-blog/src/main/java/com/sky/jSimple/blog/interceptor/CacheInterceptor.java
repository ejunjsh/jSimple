package com.sky.jSimple.blog.interceptor;

import com.sky.jSimple.blog.annotation.Cache;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;
import com.sky.jSimple.mvc.annotation.Order;
import com.sky.jSimple.utils.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Order(96)
public class CacheInterceptor extends Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);
    public Map<String, ActionResult> cacheMap = new ConcurrentHashMap<String, ActionResult>();

    public CacheInterceptor() {
        Timer timer = new Timer();
        //每十分钟执行一次,更新缓存
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cacheMap.clear();
                logger.debug("clear action result map.");
            }
        }, 600000, 600000);
    }

    public Class<? extends Annotation> getAnnotation() {
        return Cache.class;
    }

    @Override
    public ActionResult before(Class<?> cls, Method method, Object[] params) {
        String key = cls.getName() + method.getName();
        if (ArrayUtil.isNotEmpty(params)) {
            for (Object o : params) {
                key += o.toString();
            }
        }
        if (cacheMap.get(key) == null) {
            return null;
        } else {
            ActionResult result = cacheMap.get(key);
            //重置一下
            result.setRequest(request);
            result.setResponse(response);

            logger.debug("action result get cache from key " + key);
            return result;
        }
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, ActionResult result) {
        if (result != null) {
            String key = cls.getName() + method.getName();
            if (ArrayUtil.isNotEmpty(params)) {
                for (Object o : params) {
                    key += o.toString();
                }
            }
            cacheMap.put(key, result);

            logger.debug("action result put cache with key " + key);
        }
    }

}
