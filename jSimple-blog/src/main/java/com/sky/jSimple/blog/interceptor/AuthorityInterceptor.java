package com.sky.jSimple.blog.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.sky.jSimple.blog.annotation.Authority;
import com.sky.jSimple.mvc.HtmlResult;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;

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
		return null;
	}

	@Override
	public boolean after(Class<?> cls, Method method) {
		// TODO Auto-generated method stub
		return false;
	}

}
