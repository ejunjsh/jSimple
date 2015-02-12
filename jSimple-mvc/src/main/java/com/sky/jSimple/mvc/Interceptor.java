package com.sky.jSimple.mvc;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.mvc.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class Interceptor implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public Class<? extends Annotation> getAnnotation() {
        return null;
    }

    public boolean getGlobal() {
        return false;
    }

    public abstract ActionResult before(Class<?> cls, Method method, Object[] params);

    public final Object doProxy(ProxyChain aspectChain) {
        Method method = aspectChain.getTargetMethod();
        Class<?> cls = aspectChain.getTargetClass();
        Object[] params = aspectChain.getMethodParams();

        if (method.isAnnotationPresent(Default.class)
                || method.isAnnotationPresent(HttpDelete.class)
                || method.isAnnotationPresent(HttpGet.class)
                || method.isAnnotationPresent(HttpPost.class)
                || method.isAnnotationPresent(HttpPut.class)) {
            if ((getAnnotation() != null && method.isAnnotationPresent(getAnnotation())) || getGlobal()) {
                request = WebContext.getRequest();
                response = WebContext.getResponse();
                Object beforeResult = before(cls, method, params);
                if (beforeResult == null) {
                    Object result = aspectChain.doProxyChain();
                    after(cls, method, params, (ActionResult) result);
                    return result;
                } else {
                    return beforeResult;
                }
            } else {
                return aspectChain.doProxyChain();
            }
        } else {
            return aspectChain.doProxyChain();
        }

    }

    public abstract void after(Class<?> cls, Method method, Object[] params, ActionResult Result);


    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

}
