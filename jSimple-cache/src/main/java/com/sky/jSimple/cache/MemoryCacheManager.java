package com.sky.jSimple.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MemoryCacheManager  implements ICacheManager{

	private Map<String,Object> cacheContainer;
	
	private static String DEFAULT_SCOPE="MEMORY";
	
	public MemoryCacheManager()
	{
		cacheContainer=new HashMap<String, Object>();
	}
	
	public synchronized void insert(String key, Object value) {
		insert(key, value,"");
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void insert(String key, Object value,String scope) {
		cacheContainer.put(key, value);
		
	    scope=StringUtils.isEmpty(scope)?DEFAULT_SCOPE:scope;
		Object object= cacheContainer.get(scope);
		
		if(object==null)
		{
			object=new ArrayList<String>();
		}
		List<String> keys=(ArrayList<String>)object;
		keys.add(key);
		cacheContainer.put(scope, keys);
	}

	public Object get(String key) {
		return cacheContainer.get(key);
	}

	public synchronized void remove(String key) {
		cacheContainer.remove(key);
	}

	@SuppressWarnings("unchecked")
	public synchronized void removeScope(String scope) {
		Object object= cacheContainer.get(scope);
		if(object!=null)
		{
			List<String> keys=(List<String>)object;
			for(String key:keys)
			{
				cacheContainer.remove(key);
			}
			cacheContainer.remove(scope);
		}
	}   
}
