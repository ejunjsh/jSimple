package com.sky.jSimple.blog.interceptor;

import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;
import com.sky.jSimple.mvc.annotation.Order;

import java.lang.reflect.Method;

@Order(98)
public class SecondGlobalInterceptor extends Interceptor {

    @Override
    public boolean getGlobal() {
        return true;
    }

    @Override
    public ActionResult before(Class<?> cls, Method method) {
        return null;
    }

    @Override
    public boolean after(Class<?> cls, Method method, Object result) {
        // TODO Auto-generated method stub
        return false;
    }

}
