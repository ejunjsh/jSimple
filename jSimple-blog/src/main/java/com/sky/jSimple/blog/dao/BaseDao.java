package com.sky.jSimple.blog.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.sky.jSimple.data.DBHelper;
import com.sky.jSimple.data.EntityHelper;
import com.sky.jSimple.data.SQLHelper;
import com.sky.jSimple.exception.JSimpleException;


public class BaseDao<T> {
	
	 private Class<T> entityClass;
		
		@SuppressWarnings("unchecked")
		public BaseDao()
		{
			 entityClass =(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	 
	 public long insert(T entity) throws JSimpleException
	 {
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(entityClass);
	     String sql=SQLHelper.generateInsertSQL(entityClass,entityMap.keySet());
	     long id= (Long)DBHelper.insertReturnPK(sql,EntityHelper.entityToArray(entity));
	     return id;
	 }
	   

	public void update(T entity) throws JSimpleException
	 {
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(entityClass);
	     String sql=SQLHelper.generateUpdateSQL(entityClass,entityMap.keySet(),"id=?");
	     DBHelper.update(sql, EntityHelper.entityToArray(entity));
	 }
	   
	 public void delete(Long id) throws JSimpleException
	 {
	   String sql=SQLHelper.generateDeleteSQL(entityClass, "id=?");
	   DBHelper.update(sql, id);
	 }
	   
	 public  T getById(Long id) throws JSimpleException
	 {
		 String sql=SQLHelper.generateSelectSQL(entityClass, "id=?", "");
		 return DBHelper.queryBean(entityClass, sql, id);
	 }
	   
	 public List<T> getPager(int pageNumber,int pageSize,String condition,String sort) throws JSimpleException
	 {
		 String sql=SQLHelper.generateSelectSQLForPager(pageNumber, pageSize, entityClass, condition, sort);
		 return DBHelper.queryBeanList(entityClass, sql, null);
	 }
	 
	 public long getCount(String condition) throws JSimpleException
	 {
		 String sql=SQLHelper.generateSelectSQLForCount(entityClass, condition);
		 return DBHelper.queryCount(sql, null);
	 }
}
