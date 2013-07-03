package com.sky.jSimple.blog.interceptor;

import com.sky.jSimple.blog.annotation.Authority;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;
import com.sky.jSimple.mvc.TextResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AuthorityInterceptor extends Interceptor {

	@Override
	public Class<? extends Annotation> getAnnotation() {
		// TODO Auto-generated method stub
		return Authority.class;
	}

	@Override
	public boolean getGlobal() {
		return false;
	}

	@Override
	public ActionResult before(Class<?> cls, Method method) {
		return new TextResult("no authority!");
	}

	@Override
	public boolean after(Class<?> cls, Method method) {
		// TODO Auto-generated method stub
		return false;
	}

}
