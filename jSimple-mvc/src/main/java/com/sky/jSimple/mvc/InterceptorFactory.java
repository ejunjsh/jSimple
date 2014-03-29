package com.sky.jSimple.mvc;

import java.util.ArrayList;
import java.util.List;

import com.sky.jSimple.aop.AOPFactory;
import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;

public class InterceptorFactory implements IProxyFactory {

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
					proxyList.add((Proxy) inerceptorClass.newInstance());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return proxyList;
		}
		return null;

	}
}
