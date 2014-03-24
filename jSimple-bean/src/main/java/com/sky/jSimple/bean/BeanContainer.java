package com.sky.jSimple.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.jSimple.Annotation.Bean;

public class BeanContainer {
	
	private static BeanContainer instance;
	
	static
	{
		instance=new BeanContainer();
		instance.container=new HashMap<Class<?>, Object>(50);
	}
	
	
	private  Map<Class<?>,Object> container;
	
	
    
    @SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
        if (!instance.container.containsKey(cls)) {
            return null;
        }
        return (T) instance.container.get(cls);
    }

    public static void setBean(Class<?> cls, Object obj) {
    	
    	instance.container.put(cls, obj);
    }
    
    public static Map<Class<?>,Object> getMap()
    {
    	
    	return instance.container;
    }
}
