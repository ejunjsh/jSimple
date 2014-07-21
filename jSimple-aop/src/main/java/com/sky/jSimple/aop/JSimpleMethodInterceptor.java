package com.sky.jSimple.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class JSimpleMethodInterceptor implements MethodInterceptor,Serializable {
	
	  /** 
	* @Fields serialVersionUID : description
	*/ 
	private static final long serialVersionUID = -7904575475399812891L;
	private List<Proxy> proxyList;
	  
	  public JSimpleMethodInterceptor(List<Proxy> proxies)
	  {
		  proxyList=proxies;
	  }
	  
	  public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
          return new ProxyChain( targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
      }
}
