package com.sky.jSimple.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AOPFactory {
	private static final Logger logger = LoggerFactory.getLogger(AOPFactory.class);
    @SuppressWarnings("unchecked")
    public static <T> T createEnhanceObject(final Class<T> targetClass, final List<Proxy> proxyList) {
    	
    	logger.debug("[jSimple]-create AOP Object--"+targetClass.getName());
    	//add serializable interface for save this enhancer object to other place.
    	Class<?>[] interfaces={Serializable.class};
    	JSimpleMethodInterceptor methodInterceptor=new JSimpleMethodInterceptor(proxyList);
        return (T) Enhancer.create(targetClass,interfaces,methodInterceptor);
    }
}
