package com.sky.jSimple.cache;

public interface ICacheManager {
	   void insert(String key,Object value);
	   Object get(String key);
       void remove(String key);
       void removeScope(String scope);
	void insert(String key, Object value, String scope);
}
