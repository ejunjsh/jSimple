package com.sky.jSimple.cache;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCacheManager  implements ICacheManager{

	private Map<String,Object> cacheContainer;
	
	private static String DEFAULT_SCOPE="MEMORY";
	
	public MemoryCacheManager()
	{
		cacheContainer=new HashMap<String, Object>();
	}
	
	public synchronized void insert(String key, Object value,int expire) {
		insert(key, value,"",expire);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void insert(String key, Object value,String scope,int expire) {
		cacheContainer.put(key, value);
		
	    scope=StringUtils.isEmpty(scope)?DEFAULT_SCOPE:scope;

        //多scope
        String[] ss=scope.split(",");

        for (String s : ss) {
            Object object = cacheContainer.get(s);

            if (object == null) {
                object = new ArrayList<String>();
            }
            List<String> keys = (ArrayList<String>) object;
            keys.add(key);
            cacheContainer.put(s, keys);
        }
	}

	public Object get(String key) {
		return cacheContainer.get(key);
	}

	public synchronized void remove(String key) {
		cacheContainer.remove(key);
	}

	@SuppressWarnings("unchecked")
	public synchronized void removeScope(String scope) {
        //多scope
        String[] ss=scope.split(",");

        for (String s : ss) {
            Object object = cacheContainer.get(s);
            if (object != null) {
                List<String> keys = (List<String>) object;
                for (String key : keys) {
                    cacheContainer.remove(key);
                }
                cacheContainer.remove(s);
            }
        }
	}   
}
