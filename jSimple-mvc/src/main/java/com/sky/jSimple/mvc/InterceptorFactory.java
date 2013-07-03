package com.sky.jSimple.mvc;

import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InterceptorFactory implements IProxyFactory {

	private static final Logger logger = LoggerFactory.getLogger(InterceptorFactory.class);
	
	public List<Proxy> create(Class<?> cls) throws JSimpleException {
		List<Class<?>> clsClasses= ClassScaner.getClassListBySuper(IController.class);
		if (clsClasses.contains(cls)) {
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
					throw new JSimpleException(e);
					
				} catch (IllegalAccessException e) {
					logger.debug("[jSimple]--create proxy error");
					throw new JSimpleException(e);
				}
			}
			return proxyList;
		}
		return null;

	}
}
