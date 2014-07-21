package com.sky.jSimple.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sky.jSimple.exception.JSimpleException;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();
    private int proxyIndex = 0;

    public ProxyChain( Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> aspects) {
        this.targetClass = targetObject.getClass();
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = aspects;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }
    
    public Object getTargetObject()
    {
    	return targetObject;
    }

    public Object doProxyChain() throws JSimpleException   {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            try {
				methodResult = methodProxy.invokeSuper(targetObject, methodParams);
			} catch (Throwable e) {
				throw new JSimpleException(e);
			}
        }
        return methodResult;
    }
}