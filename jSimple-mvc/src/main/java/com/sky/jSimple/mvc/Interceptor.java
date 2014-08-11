package com.sky.jSimple.mvc;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class Interceptor implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

    public abstract Class<? extends Annotation> getAnnotation();

    public abstract boolean getGlobal();

    public abstract ActionResult before(Class<?> cls, Method method);

    public final Object doProxy(ProxyChain aspectChain) throws JSimpleException {
        Method method = aspectChain.getTargetMethod();
        Class<?> cls = aspectChain.getTargetClass();

        if (method.isAnnotationPresent(getAnnotation()) || getGlobal()) {
            Object beforeResult = before(cls, method);
            if (beforeResult == null) {
                Object result = aspectChain.doProxyChain();
                after(cls, method);
                return result;
            } else {
                return beforeResult;
            }
        } else {
            return aspectChain.doProxyChain();
        }

    }

    public abstract boolean after(Class<?> cls, Method method);

    ;

}
