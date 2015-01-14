package com.sky.jSimple.cache;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.cache.annotation.Cache;
import com.sky.jSimple.cache.annotation.Evict;
import com.sky.jSimple.exception.JSimpleException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.text.MessageFormat;

public class CacheProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(CacheProxy.class);
    private ICacheManager cacheManager;

    public ICacheManager getCacheManager() {
        if (cacheManager == null) {
            //MemoryCacheManager as default.
            cacheManager = new MemoryCacheManager();
        }
        return cacheManager;
    }

    public void setCacheManager(ICacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        try {
            Class<?> cls = proxyChain.getTargetClass();
            Method method = proxyChain.getTargetMethod();
            Object[] params = proxyChain.getMethodParams();

            if (method.isAnnotationPresent(Cache.class)) {
                String scope = method.getAnnotation(Cache.class).scope();
                //实现动态scope -- myscope{0}{1}
                scope = MessageFormat.format(scope, params);

                int expire = method.getAnnotation(Cache.class).expire();

                if (StringUtils.isBlank(scope)) {
                    scope = cls.getName();
                }
                String key = method.getAnnotation(Cache.class).key();
                if (StringUtils.isBlank(key)) {
                    key = method.getName();
                    for (Object param : proxyChain.getMethodParams()) {
                        key += param.toString();
                    }
                }
                result = getCacheManager().get(key);
                if (result != null) {
                    logger.debug("[jSimpe-cache] get cache from key--" + key);
                    return result;
                }
                result = proxyChain.doProxyChain();

                getCacheManager().insert(key, result, scope, expire);
                logger.debug("[jSimpe-cache] save cache with key--" + key + " in scope--" + scope);
                return result;
            } else if (method.isAnnotationPresent(Evict.class)) {
                String scope = method.getAnnotation(Evict.class).scope();

                //实现动态scope -- myscope{0}{1}
                scope = MessageFormat.format(scope, params);
                if (StringUtils.isBlank(scope)) {
                    scope = cls.getName();
                }
                getCacheManager().removeScope(scope);
                logger.debug("[jSimpe-cache] remove all cache in scope--" + scope);
                String key = method.getAnnotation(Evict.class).key();
                if (StringUtils.isNotBlank(key)) {
                    getCacheManager().remove(key);
                    logger.debug("[jSimpe-cache] remove cache from key--" + key);
                }

                return proxyChain.doProxyChain();
            } else {
                return proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            throw new JSimpleException(e);
        }
    }
}
