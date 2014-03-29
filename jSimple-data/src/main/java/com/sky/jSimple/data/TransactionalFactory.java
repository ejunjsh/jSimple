package com.sky.jSimple.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.data.annotation.Transactional;

public class TransactionalFactory implements IProxyFactory {

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
		    proxies.add(new TransactionalProxy());
		}
		if(proxies.size()>0){
			return proxies;
		}
		else {
			return null;
		}
	}

}
