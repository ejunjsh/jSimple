package com.sky.jSimple.aop;

import java.lang.reflect.Method;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AspectFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createAspect(final Class<?> targetClass, final List<Aspect> aspectList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new AspectChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, aspectList).doAspectChain();
            }
        });
    }
}
