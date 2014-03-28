package com.sky.jSimple.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.AspectChain;

public abstract class Interceptor implements Proxy {

    public abstract Class<? extends Annotation> getAnnotation();
    
    public abstract boolean getGlobal();
	
	public abstract ActionResult before(Class<?> cls,Method method);

	public final Object doProxy(AspectChain aspectChain) throws Throwable {
		Method method=aspectChain.getTargetMethod();
		Class<?> cls=aspectChain.getTargetClass();
		
		if(method.isAnnotationPresent(getAnnotation())||getGlobal())
		{
			Object befroeResult=before(cls,method);
			if(befroeResult==null)
			{
				Object result= aspectChain.doAspectChain();
				after(cls,method);
				return result;
			}
			else {
				return befroeResult;
			}
		}
		else {
			return aspectChain.doAspectChain();
		}
		
	}

	public abstract boolean after(Class<?> cls,Method method);;

}
