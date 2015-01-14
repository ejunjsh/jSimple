package com.sky.jSimple.mvc;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class ActionResult  {
	
	private static final Logger logger = LoggerFactory.getLogger(ActionResult.class);
	
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
        if (getModel() instanceof Map || getModel() instanceof Model) {
            Map<String, Object> map;
            if (getModel() instanceof Map) {
                map = (Map<String, Object>) getModel();
            } else {
                map = ((Model) getModel()).getMap();
            }
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
