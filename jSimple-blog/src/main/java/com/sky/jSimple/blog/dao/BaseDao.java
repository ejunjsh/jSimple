package com.sky.jSimple.blog.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.sky.jSimple.data.JSimpleDataTemplate;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;


public class BaseDao<T> {
	
	 private Class<T> entityClass;
		
		@SuppressWarnings("unchecked")
		public BaseDao()
		{
			 entityClass =(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	 
	@Inject	
	 private JSimpleDataTemplate jSimpleDataTemplate;	
		
	 public void  insert(T entity) throws JSimpleException
	 {
		jSimpleDataTemplate.insert(entity);
	 }
	   

	public void update(T entity) throws JSimpleException
	 {
		jSimpleDataTemplate.update(entity);
	 }
	   
	 public void delete(Long id) throws JSimpleException
	 {
	   jSimpleDataTemplate.delete(id, entityClass);
	 }
	   
	 public  T getById(Long id) throws JSimpleException
	 {
		 return jSimpleDataTemplate.getById(entityClass,id);
	 }
	
	   
	 public List<T> getPager(int pageNumber,int pageSize,String condition,String sort,Object ... params) throws JSimpleException
	 {
		return jSimpleDataTemplate.getPager(entityClass,pageNumber, pageSize, condition, sort,params);
	 }
	 
	 public long getCount(String condition,Object ... params) throws JSimpleException
	 {
		 return jSimpleDataTemplate.getCount(condition, entityClass, params);
	 }
	 
	  public List<T> getAll(String sort) throws JSimpleException
	  {
		  return jSimpleDataTemplate.queryListByCondition(entityClass,"", sort);
	  }


	public JSimpleDataTemplate getjSimpleDataTemplate() {
		return jSimpleDataTemplate;
	}


	public void setjSimpleDataTemplate(JSimpleDataTemplate jSimpleDataTemplate) {
		this.jSimpleDataTemplate = jSimpleDataTemplate;
	}
}
