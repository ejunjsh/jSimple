package com.sky.jSimple.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;

public abstract class Interceptor implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);
    public abstract Class<? extends Annotation> getAnnotation();
    
    public abstract boolean getGlobal();
	
	public abstract ActionResult before(Class<?> cls,Method method);

	public final Object doProxy(ProxyChain aspectChain) throws Throwable {
		Method method=aspectChain.getTargetMethod();
		Class<?> cls=aspectChain.getTargetClass();
		
		if(method.isAnnotationPresent(getAnnotation())||getGlobal())
		{
			Object befroeResult=before(cls,method);
			if(befroeResult==null)
			{
				Object result= aspectChain.doProxyChain();
				after(cls,method);
				return result;
			}
			else {
				return befroeResult;
			}
		}
		else {
			return aspectChain.doProxyChain();
		}
		
	}

	public abstract boolean after(Class<?> cls,Method method);;

}
