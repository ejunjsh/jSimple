package com.sky.jSimple.aop.test;

import java.lang.reflect.Method;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.aop.annotation.Cache;
import com.sky.jSimple.aop.annotation.Log;
import com.sky.jSimple.exception.JSimpleException;

public class CacheAspect implements Proxy {

	public boolean before(Class<?> cls, Method method, Object[] params) {
		System.out.println("cache before");
		return true;
	}

	public Object doProxy(ProxyChain aspectChain) throws JSimpleException  {
		Class<?> cls=aspectChain.getTargetClass();
		Method method=aspectChain.getTargetMethod();
		Object[] params=aspectChain.getMethodParams();
	  if(method.isAnnotationPresent(Cache.class))
	  {
		  if(before(cls, method, params))
		  {
			 Object object= aspectChain.doProxyChain();
			  after(cls, method, params);
			  return object;
		  }
		  return null;
	  }
	  else {
		  return aspectChain.doProxyChain();
	  }
	}

	public boolean after(Class<?> cls, Method method, Object[] params) {
		System.out.println("cache after");
		return true;
	}

}
