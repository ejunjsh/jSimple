package com.sky.jSimple.mvc;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;

public abstract class ActionResult {
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	private Object model;
	
	public ActionResult()
	{
		request=WebContext.getRequest();
	    response=WebContext.getResponse();
	}
	
	public abstract void ExecuteResult();

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
	
	public void processRequest()
	{
		if(getModel() instanceof Map)
		{
			Map<String,Object> map=(Map<String,Object>)getModel();
			for(Map.Entry<String, Object> entry:map.entrySet())
			{
				
				request.setAttribute(entry.getKey(),entry.getValue());
			}
		}
		else {
			  BeanMap beanMap =new BeanMap(getModel());
			  
			   Set keys = beanMap.keySet();
				Iterator keyIterator = keys.iterator();
				while(keyIterator.hasNext()){
					String key = (String)keyIterator.next();
					request.setAttribute(key,beanMap.get(key));
				}
				
		}
	}
}
