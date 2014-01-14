package com.sky.jSimple.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.data.annotation.GetBy;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;

public class JSimpleDataBeanProxy implements Proxy {

	private SessionFactory sessionFactory;
	
	public JSimpleDataBeanProxy(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}
	
	public Object doProxy(ProxyChain proxyChain) throws JSimpleException {
		Method method = proxyChain.getTargetMethod();
	    Object object=proxyChain.getTargetObject();
		Object result = null;
        if (method.isAnnotationPresent(GetBy.class)) {
            String condition= method.getAnnotation(GetBy.class).condition();
            String values=method.getAnnotation(GetBy.class).values();
            
            Class<?> returnClass=null;
            Type returntType=method.getGenericReturnType();
            if(returntType instanceof ParameterizedType)
            {
            	
            }
            else {
				returnClass=method.getReturnType();
				
			}
        }
        else {
        	 result = proxyChain.doProxyChain();
		}
        return result;
	}

}
