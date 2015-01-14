package com.sky.jSimple.cache;

import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.cache.annotation.Cache;
import com.sky.jSimple.cache.annotation.Evict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CacheProxyFactory implements IProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(CacheProxyFactory.class);

    public List<Proxy> create(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        boolean flag = false;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Cache.class) || method.isAnnotationPresent(Evict.class)) {
                flag = true;
                break;
            }
        }
        List<Proxy> proxies = new ArrayList<Proxy>();
        if (flag) {
            if (BeanContainer.getBean(CacheProxy.class) == null) {
                Object proxy = new CacheProxy();
                BeanContainer.setBean(CacheProxy.class, proxy);
                proxies.add((Proxy) proxy);
            } else {
                proxies.add(BeanContainer.getBean(CacheProxy.class));
            }

        }
        if (proxies.size() > 0) {
            return proxies;
        } else {
            return null;
        }
    }

}
