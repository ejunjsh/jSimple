package com.sky.jSimple.mvc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.aop.AOPFactory;
import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;

public class InterceptorFactory implements IProxyFactory {

	private static final Logger logger = LoggerFactory.getLogger(InterceptorFactory.class);
	
	public List<Proxy> create(Class<?> cls) {
		Class<?>[] clsClasses= cls.getSuperclass().getInterfaces();
		boolean flag=false;
		for(Class<?> c:clsClasses)
		{
			if(c==IController.class)
			{
				flag=true;
				break;
			}
		}
		if (flag) {
			List<Class<?>> interceptorClasses = ClassScaner
					.getClassListBySuper(Interceptor.class);
			List<Proxy> proxyList = new ArrayList<Proxy>();
			for (Class<?> inerceptorClass : interceptorClasses) {
				try {
					if(BeanContainer.getBean(inerceptorClass)==null)
					{
						Object proxy=inerceptorClass.newInstance();
						BeanContainer.setBean(inerceptorClass, proxy);
						proxyList.add((Proxy)proxy);
					}
					else {
						proxyList.add((Proxy)BeanContainer.getBean(inerceptorClass));
					}
					
					
				} catch (InstantiationException e) {
					logger.debug("[jSimple]--create proxy error");
				} catch (IllegalAccessException e) {
					logger.debug("[jSimple]--create proxy error");
				}
			}
			return proxyList;
		}
		return null;

	}
}
