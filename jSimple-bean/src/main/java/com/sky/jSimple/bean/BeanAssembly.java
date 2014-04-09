package com.sky.jSimple.bean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.aop.AOPFactory;
import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.exception.JSimpleException;

public class BeanAssembly {
	private static final Logger logger = LoggerFactory.getLogger(BeanAssembly.class);
     public static void assemble() throws JSimpleException 
     {
    	 List<Class<?>> clsList=ClassScaner.getClassList();
    	 List<Class<?>> proxyFactoryList=ClassScaner.getClassListBySuper(IProxyFactory.class);
    	 for (Class<?> cls : clsList) {
    		 if(cls.isAnnotationPresent(Bean.class))
    		 {
	    		 List<Proxy> proxyList=new ArrayList<Proxy>();
				for(Class<?> factory:proxyFactoryList)
				{
					try {
						BeanContainer.setBean(factory,factory.newInstance());
					} catch (InstantiationException e) {
						throw new JSimpleException(e);
					} catch (IllegalAccessException e) {
						throw new JSimpleException(e);
					}
					IProxyFactory factoryInstance=(IProxyFactory) BeanContainer.getBean(factory);
					List<Proxy> pList=factoryInstance.create(cls);
					if(pList!=null&&pList.size()>0)
					proxyList.addAll(pList);
				}
				if(proxyList!=null&&proxyList.size()>0)
				{
					logger.debug("[jSimple]--create instance of"+cls.getName());
				   BeanContainer.setBean(cls,AOPFactory.createEnhanceObject(cls, proxyList));
				}
				else {
						try {
							BeanContainer.setBean(cls,cls.newInstance());
						} catch (InstantiationException e) {
						    throw new JSimpleException(e);
						} catch (IllegalAccessException e) {
							throw new JSimpleException(e);
							}
					
					
				}
    		 }
		}
     }
}
