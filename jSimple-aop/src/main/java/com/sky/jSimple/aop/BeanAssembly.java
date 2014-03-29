package com.sky.jSimple.aop;

import java.util.ArrayList;
import java.util.List;

import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;

public class BeanAssembly {
     public static void assemble() throws InstantiationException, IllegalAccessException
     {
    	 List<Class<?>> clsList=ClassScaner.getClassList();
    	 List<Class<?>> proxyFactoryList=ClassScaner.getClassListBySuper(IProxyFactory.class);
    	 for (Class<?> cls : clsList) {
    		 if(!cls.isAnnotation()&&!cls.isInterface())
    		 {
	    		 List<Proxy> proxyList=new ArrayList<Proxy>();
				for(Class<?> factory:proxyFactoryList)
				{
					BeanContainer.setBean(factory,factory.newInstance());
					IProxyFactory factoryInstance=(IProxyFactory) BeanContainer.getBean(factory);
					List<Proxy> pList=factoryInstance.create(cls);
					if(pList!=null&&pList.size()>0)
					proxyList.addAll(pList);
				}
				if(proxyList!=null&&proxyList.size()>0)
				{
				   BeanContainer.setBean(cls,AOPFactory.createEnhanceObject(cls, proxyList));
				}
				else {
					try {
						BeanContainer.setBean(cls,cls.newInstance());
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
    		 }
		}
     }
}
