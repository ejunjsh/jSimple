package com.sky.jSimple.aop.test;

import java.lang.reflect.Method;

import com.sky.jSimple.aop.Aspect;
import com.sky.jSimple.aop.AspectChain;
import com.sky.jSimple.aop.annotation.Log;

public class LogAspect implements Aspect {

	public boolean before(Class<?> cls, Method method, Object[] params) {
		System.out.println("log before");
		return true;
	}

	public Object doAspect(AspectChain aspectChain) throws Throwable {
		Class<?> cls=aspectChain.getTargetClass();
		Method method=aspectChain.getTargetMethod();
		Object[] params=aspectChain.getMethodParams();
	  if(method.isAnnotationPresent(Log.class))
	  {
		  if(before(cls, method, params))
		  {
			 Object object= aspectChain.doAspectChain();
			  after(cls, method, params);
			  return object;
		  }
		  return null;
	  }
	  else {
		  return aspectChain.doAspectChain();
	  }
	}

	public boolean after(Class<?> cls, Method method, Object[] params) {
		System.out.println("log after");
		return true;
	}

}
