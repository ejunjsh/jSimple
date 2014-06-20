package com.sky.jSimple.data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sky.jSimple.data.annotation.Column;
import com.sky.jSimple.data.annotation.Id;
import com.sky.jSimple.exception.JSimpleException;

public class JSimpleDataTemplate {

   private ConnectionFactory connectionFactory;
	
   public void insert(Object entity) throws JSimpleException
   {
	   Connection connection=connectionFactory.getConnection();
	   
	   Map<String, String> entityMap= EntityHelper.getEntityMap().get(entity.getClass());
	     String sql=SQLHelper.generateInsertSQL(entity.getClass(),entityMap.keySet());
	     long id= (Long)DBHelper.insertReturnPK(connection,sql,EntityHelper.entityToArray(entity));
	     try {
			Field[] fields= entity.getClass().getDeclaredFields();
			for(Field field : fields)
			{
				if(field.isAnnotationPresent(Id.class))
				{
					field.setAccessible(true) ;
					field.set(entity,id);
				}
				break;
			}
			
		} catch (Exception e) {
			throw new JSimpleException(e);
		} 
   }
   
   public void update(Object entity) throws JSimpleException
   {
	   Connection connection=connectionFactory.getConnection();
	   String idCondition="=?";
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(entity.getClass());
		 List<Object> objects=EntityHelper.entityToList(entity);
		 for(String s:entityMap.values())
		 {
			 Field field;
			try {
				field = entity.getClass().getDeclaredField(s);
				 if(field.isAnnotationPresent(Id.class))
				 {
					 field.setAccessible(true);
			   	     objects.add(field.get(entity));
			   	     
			   	    String columnName ="";
			   	    Column column = field.getAnnotation(Column.class);
			   	    if(column!=null)
			   	    {
			   	    	columnName=column.value();
			   	    }
			   	    
			   	    if(columnName!=null&&!columnName.isEmpty())
			   	    {
			   	    	idCondition=columnName+idCondition;
			   	    }
			   	    else {
			   	    	idCondition=field.getName()+idCondition;
					}
			   	    break;
				 }
			}  catch (Exception e) {
				new JSimpleException(e);
			}
			
		 }
		
	     String sql=SQLHelper.generateUpdateSQL(entity.getClass(),entityMap.keySet(),idCondition);

	     DBHelper.update(connection,sql,objects.toArray());
   }
   
   public  void delete(Object id,Class<?> entityClass) throws JSimpleException
   {
	   
       Connection connection=connectionFactory.getConnection();
	   
	   String idCondition="=?";
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(entityClass);
		 for(String s:entityMap.values())
		 {
			 Field field;
			try {
				field = entityClass.getDeclaredField(s);
				 if(field.isAnnotationPresent(Id.class))
				 {
					 String columnName ="";
				   	    Column column = field.getAnnotation(Column.class);
				   	    if(column!=null)
				   	    {
				   	    	columnName=column.value();
				   	    }
			   	    
			   	    if(columnName!=null&&!columnName.isEmpty())
			   	    {
			   	    	idCondition=columnName+idCondition;
			   	    }
			   	    else {
			   	    	idCondition=field.getName()+idCondition;
					}
			   	    break;
				 }
			}  catch (Exception e) {
				new JSimpleException(e);
			}
			
		 }
	   
	   String sql=SQLHelper.generateDeleteSQL(entityClass, idCondition);
	   DBHelper.update(connection,sql, id);
   }
   
   public <T> T querySingleByCondition(Class<T> cls , String condition,Object ... params) throws JSimpleException
   {
	   Connection connection=connectionFactory.getConnection();
	  
	   String sql=SQLHelper.generateSelectSQL(cls, condition,"");
		 return DBHelper.queryBean(connection,cls, sql, params);
   }
   
   public <T> List<T> queryListByCondition(Class<T> cls ,String condition,String sort,Object ... params) throws JSimpleException
   {
	   Connection connection=connectionFactory.getConnection();

	   String sql=SQLHelper.generateSelectSQL(cls, condition, sort);
		 return DBHelper.queryBeanList(connection,cls, sql, params);
   }
   
   public <T> List<T> queryList(Class<T> cls ,String sql,Object ... params) throws JSimpleException
   {
	   Connection connection=connectionFactory.getConnection();
		 return DBHelper.queryBeanList(connection,cls, sql, params);
   }
   
   public <T> T querySingle(Class<T> cls, String sql,Object ... params) throws JSimpleException
   {
	   Connection connection=connectionFactory.getConnection();
  
		 return DBHelper.queryBean(connection,cls, sql, params);
   }
   
   public  <T> T getById(Class<T> cls, Long id) throws JSimpleException
	 {
	   Connection connection=connectionFactory.getConnection();
	   
	   String idCondition="=?";
		 Map<String, String> entityMap= EntityHelper.getEntityMap().get(cls);
		 for(String s:entityMap.values())
		 {
			 Field field;
			try {
				field = cls.getDeclaredField(s);
				 if(field.isAnnotationPresent(Id.class))
				 {
					 String columnName ="";
				   	    Column column = field.getAnnotation(Column.class);
				   	    if(column!=null)
				   	    {
				   	    	columnName=column.value();
				   	    }
			   	    
			   	    if(columnName!=null&&!columnName.isEmpty())
			   	    {
			   	    	idCondition=columnName+idCondition;
			   	    }
			   	    else {
			   	    	idCondition=field.getName()+idCondition;
					}
			   	    break;
				 }
			}  catch (Exception e) {
				new JSimpleException(e);
			}
			
		 }
	   
	   String sql=SQLHelper.generateSelectSQL(cls,idCondition, "");
	   return DBHelper.queryBean(connection,cls, sql, id);
	 }
   
   public <T> List<T> getPager(Class<T> cls, int pageNumber,int pageSize,String condition,String sort,Object ... params) throws JSimpleException
	 {
	   Connection connection=connectionFactory.getConnection();
   
		 String sql=SQLHelper.generateSelectSQLForPager(pageNumber, pageSize, cls, condition, sort);
		 return DBHelper.queryBeanList(connection,cls, sql, params);
	 }
	 
	 public long getCount(String condition,Class<?> entityClass,Object ... params) throws JSimpleException
	 {
		 Connection connection=connectionFactory.getConnection();
		   
		 String sql=SQLHelper.generateSelectSQLForCount(entityClass, condition);
		 return DBHelper.queryCount(connection,sql, params);
	 }

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
}
