package com.sky.jSimple.mvc.bean;

import java.lang.reflect.Method;
import java.util.List;

public class ControllerBean {
	 private Class<?> controllerClass;
	    private Method actionMethod;
	    
	    private List<String> paramNames;

	    public ControllerBean(Class<?> controllerClass, Method actionMethod,List<String> paramNames) {
	        this.controllerClass = controllerClass;
	        this.actionMethod = actionMethod;
	        this.paramNames=paramNames;
	    }


	    public Method getActionMethod() {
	        return actionMethod;
	    }

		public Class<?> getControllerClass() {
			return controllerClass;
		}


		public List<String> getParamNames() {
			return paramNames;
		}

}
