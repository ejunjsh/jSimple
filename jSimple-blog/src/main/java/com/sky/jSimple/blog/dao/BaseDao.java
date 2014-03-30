package com.sky.jSimple.blog.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.sky.jSimple.data.DBHelper;
import com.sky.jSimple.data.EntityHelper;
import com.sky.jSimple.data.SQLHelper;


public class BaseDao<T> {
	
	 private Class<T> entityClass;
		
		@SuppressWarnings("unchecked")
		public BaseDao()
		{
			 entityClass =(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	 
	 public void insert(T entity)
	 {
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(entityClass);
	     String sql=SQLHelper.generateInsertSQL(entityClass,entityMap.keySet());
	     long id= (Long)DBHelper.insertReturnPK(sql, entityMap.values());
	     entity=getById(id);
	 }
	   
	 public void update(T entity)
	 {
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(entityClass);
	     String sql=SQLHelper.generateUpdateSQL(entityClass,entityMap.keySet(),"");
	     DBHelper.update(sql, entityMap.values());
	 }
	   
	 public void delete(Long id)
	 {
	   String sql=SQLHelper.generateDeleteSQL(entityClass, "id=?");
	   DBHelper.update(sql, id);
	 }
	   
	 public  T getById(Long id)
	 {
		 String sql=SQLHelper.generateSelectSQL(entityClass, "id=?", "");
		 return DBHelper.queryBean(entityClass, sql, id);
	 }
	   
	 public List<T> getPager(int pageNumber,int pageSize,String condition,String sort)
	 {
		 String sql=SQLHelper.generateSelectSQLForPager(pageNumber, pageSize, entityClass, condition, sort);
		 return DBHelper.queryBeanList(entityClass, sql, null);
	 }
	 
	 public long getCount(String condition)
	 {
		 String sql=SQLHelper.generateSelectSQLForCount(entityClass, condition);
		 return DBHelper.queryCount(sql, null);
	 }
}
