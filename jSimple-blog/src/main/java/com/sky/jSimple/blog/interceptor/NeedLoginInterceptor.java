package com.sky.jSimple.blog.interceptor;

import com.sky.jSimple.blog.annotation.Login;
import com.sky.jSimple.blog.utils.BlogContext;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;
import com.sky.jSimple.mvc.RedirectResult;
import com.sky.jSimple.mvc.annotation.Order;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Order(98)
public class NeedLoginInterceptor extends Interceptor {

    public Class<? extends Annotation> getAnnotation() {
        return Login.class;
    }

    @Override
    public ActionResult before(Class<?> cls, Method method, Object[] params) {
        if (BlogContext.getUser() == null) {
            return new RedirectResult("/user/login?path=" + request.getAttribute("encodeUrl"));
        }
        return null;
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, ActionResult result) {
    }

}
