package com.sky.jSimple.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.data.annotation.Transactional;

public class TransactionalFactory implements IProxyFactory {
	private static final Logger logger = LoggerFactory.getLogger(TransactionalFactory.class);
	public List<Proxy> create(Class<?> cls) {
		Method[] methods =cls.getDeclaredMethods();
		boolean flag=false;
		for(Method method :methods)
		{
			if(method.isAnnotationPresent(Transactional.class))
			{
				flag=true;
				break;
			}
		}
		List<Proxy> proxies=new ArrayList<Proxy>();
		if(flag)
		{
			if(BeanContainer.getBean(TransactionalProxy.class)==null)
			{ 
				Object proxy=new TransactionalProxy();
				BeanContainer.setBean(TransactionalProxy.class, proxy);
				proxies.add((Proxy)proxy);
			}
			else {
				proxies.add((Proxy)BeanContainer.getBean(TransactionalProxy.class));
			}
		   
		}
		if(proxies.size()>0){
			return proxies;
		}
		else {
			return null;
		}
	}

}
