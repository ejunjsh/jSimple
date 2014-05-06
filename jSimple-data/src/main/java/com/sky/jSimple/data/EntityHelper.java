package com.sky.jSimple.data;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javassist.expr.NewArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.config.jSimpleConfig;
import com.sky.jSimple.data.annotation.Column;
import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.Ignore;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.ArrayUtil;
import com.sky.jSimple.utils.MapUtil;
import com.sky.jSimple.utils.StringUtil;

public class EntityHelper {

	private static final Logger logger = LoggerFactory.getLogger(EntityHelper.class);
    private static final Map<Class<?>, Map<String, String>> entityMap = new HashMap<Class<?>, Map<String, String>>(); // Entity 类 => (列名 => 字段名)

    static {
        // 获取并遍历所有 Entity 类
        List<Class<?>> entityClassList =ClassScaner.getClassListByAnnotation(Entity.class);
        for (Class<?> entityClass : entityClassList) {
            // 获取并遍历该 Entity 类中所有的字段（不包括父类中的方法）
            Field[] fields = entityClass.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(fields)) {
                // 创建一个 Field Map（用于存放列名与字段名的映射关系）
                Map<String, String> fieldMap = new LinkedHashMap<String, String>();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    String columnName;
                    if(!field.isAnnotationPresent(Ignore.class))
                    {
                    // 若该字段上存在 @Column 注解，则优先获取注解中的列名
                    if (field.isAnnotationPresent(Column.class)) {
                        columnName = field.getAnnotation(Column.class).value();
                    } else {
                        columnName =fieldName;
                    }
                    fieldMap.put(columnName, fieldName);
                    }
                    // 若字段名与列名不同，则需要进行映射
//                    if (!fieldName.equals(columnName)) {
                        
//                    }
                }
                // 将 Entity 类与 Field Map 放入 Entity Map 中
                if (MapUtil.isNotEmpty(fieldMap)) {
                    entityMap.put(entityClass, fieldMap);
                }
            }
        }
    }

    public static Map<Class<?>, Map<String, String>> getEntityMap() {
        return entityMap;
    }
    
    public static Object[] entityToArray(Object entity) throws JSimpleException 
    {
    	 Field[] fields = entity.getClass().getDeclaredFields();
    	 List<Object> objects=new ArrayList<Object>();
    	 for(int i=0;i<fields.length;i++)
    	 {
    		 try {
    			 if(!fields[i].isAnnotationPresent(Ignore.class))
    			 {
    			 fields[i].setAccessible(true);
    			 objects.add(fields[i].get(entity));
    			 }
			} catch (IllegalArgumentException e) {
				throw new JSimpleException(e);
			} catch (IllegalAccessException e) {
			    throw new JSimpleException(e);
			}
    	 }
    	 return objects.toArray();
    }
    
    public static List<Object> entityToList(Object entity) throws JSimpleException 
    {
    	 Field[] fields = entity.getClass().getDeclaredFields();
    	 List<Object> objects=new ArrayList<Object>();
    	 for(int i=0;i<fields.length;i++)
    	 {
    		 try {
    			 if(!fields[i].isAnnotationPresent(Ignore.class))
    			 {
    			 fields[i].setAccessible(true);
    			 objects.add(fields[i].get(entity));
    			 }
			} catch (IllegalArgumentException e) {
				throw new JSimpleException(e);
			} catch (IllegalAccessException e) {
			    throw new JSimpleException(e);
			}
    	 }
    	 return objects;
    }
}
