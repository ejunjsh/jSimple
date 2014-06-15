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
	
	private static List<Class<?>> clsList=ClassScaner.getClassList();
	private static  List<Class<?>> proxyFactoryList=ClassScaner.getClassListBySuper(IProxyFactory.class);
     public static void assemble() throws JSimpleException 
     {
    	 
    	 for (Class<?> cls : clsList) {
    		assemble(cls);
		}
     }
     
     public static Object assemble(Class<?> cls) throws JSimpleException
     {
    	 Object result=BeanContainer.getBean(cls);
    	 if(cls.isAnnotationPresent(Bean.class)&&result==null)
		 {
    		 List<Proxy> proxyList=new ArrayList<Proxy>();
			for(Class<?> factory:proxyFactoryList)
			{
				try {
					if(BeanContainer.getBean(factory)==null)
					BeanContainer.setBean(factory,factory.newInstance());
				} catch (Exception e) {
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
				result=AOPFactory.createEnhanceObject(cls, proxyList);
			   BeanContainer.setBean(cls,result);
			}
			else {
					try {
						result=cls.newInstance();
						BeanContainer.setBean(cls,result);
					} catch (Exception e) {
					    throw new JSimpleException(e);
					}
			}
		 }
    	 return result;
     }
     
     public static Object assemble(String name, Class<?> cls) throws JSimpleException
     {
    	 Object result=BeanContainer.getBean(name);
    	 if(result==null)
		 {
    		 List<Proxy> proxyList=new ArrayList<Proxy>();
			for(Class<?> factory:proxyFactoryList)
			{
				try {
					if(BeanContainer.getBean(factory)==null)
					BeanContainer.setBean(factory,factory.newInstance());
				} catch (Exception e) {
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
				result=AOPFactory.createEnhanceObject(cls, proxyList);
			   BeanContainer.setBean(name,result);
			}
			else {
					try {
						result=cls.newInstance();
						BeanContainer.setBean(name,result);
					} catch (Exception e) {
					    throw new JSimpleException(e);
					}
			}
		 }
    	 return result;
     }
}
