package com.sky.jSimple.blog.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import com.sky.jSimple.data.JSimpleDataTemplate;
import com.sky.jSimple.exception.JSimpleException;


public class BaseDao<T> {
	
	 private Class<T> entityClass;
		
		@SuppressWarnings("unchecked")
		public BaseDao()
		{
			 entityClass =(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	 
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
		 return jSimpleDataTemplate.getById(id);
	 }
	   
	 public List<T> getPager(int pageNumber,int pageSize,String condition,String sort) throws JSimpleException
	 {
		return jSimpleDataTemplate.getPager(pageNumber, pageSize, condition, sort);
	 }
	 
	 public long getCount(String condition) throws JSimpleException
	 {
		 return jSimpleDataTemplate.getCount(condition, entityClass);
	 }


	public JSimpleDataTemplate getjSimpleDataTemplate() {
		return jSimpleDataTemplate;
	}


	public void setjSimpleDataTemplate(JSimpleDataTemplate jSimpleDataTemplate) {
		this.jSimpleDataTemplate = jSimpleDataTemplate;
	}
}
