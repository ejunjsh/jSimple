package com.sky.jSimple.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.cache.annotation.Cache;
import com.sky.jSimple.cache.annotation.Evict;
import com.sky.jSimple.exception.JSimpleException;

public class CacheProxy implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(CacheProxy.class);
	
	public ICacheManager getCacheManager() {
		if(cacheManager==null)
		{
			//MemoryCacheManager as default.
			cacheManager=new MemoryCacheManager();
		}
		return cacheManager;
	}

	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	private ICacheManager cacheManager;
	
	public Object doProxy(ProxyChain proxyChain) throws JSimpleException {
		Object result = null;
		try {
		       Class<?> cls=proxyChain.getTargetClass();
		       Method method=proxyChain.getTargetMethod();
		       
		       
		       if(method.isAnnotationPresent(Cache.class))
		       {
		    	   String scope=method.getAnnotation(Cache.class).scope();
				    if(StringUtils.isBlank(scope))
				    {
				   	  scope=cls.getName();
				    }
				    String key=method.getAnnotation(Cache.class).key();
		    	    if(StringUtils.isBlank(key))
		    	    {
		    	    	key=method.getName();
		    	    	for(Object param:proxyChain.getMethodParams())
		    	    	{
		    	    		key+=param.toString();
		    	    	}
		    	    }
		    	    result=getCacheManager().get(key);
		    	    if(result!=null)
		    	    {
		    	    	logger.debug("[jSimpe-cache] get cache from key--"+key);
		    	    	return result;
		    	    }
		    	    result= proxyChain.doProxyChain();
		    	 
		    	    getCacheManager().insert(key, result,scope);
		    	    logger.debug("[jSimpe-cache] save cache with key--"+key+" in scope--"+scope);
		    	    return result;
		       }
		       else if(method.isAnnotationPresent(Evict.class)){
		    	   String scope=method.getAnnotation(Evict.class).scope();
				    if(StringUtils.isBlank(scope))
				    {
					   	  scope=cls.getName();
				    }
				    getCacheManager().removeScope(scope);
				    logger.debug("[jSimpe-cache] remove all cache in scope--"+scope);
				    String key=method.getAnnotation(Evict.class).key();
				    if(StringUtils.isNotBlank(key))
				    {
				    	getCacheManager().remove(key);
				    	logger.debug("[jSimpe-cache] remove cache from key--"+key);
				    }
				    
				    return proxyChain.doProxyChain();
				 }
		       else {
		    	   return proxyChain.doProxyChain();
			}
		}
		catch(Exception e)
		{
			throw new JSimpleException(e);
		}
	}
}
