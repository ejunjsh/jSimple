package com.sky.jSimple.ioc;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Impl;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.utils.ArrayUtil;
import com.sky.jSimple.utils.BeanPropertyUtil;
import com.sky.jSimple.utils.ClassUtil;
import com.sky.jSimple.utils.CollectionUtil;

public class IocManager {
	
	private static final Logger logger = LoggerFactory.getLogger(IocManager.class);
	
   public static void execute() throws JSimpleException 
   {
	// 获取并遍历所有的 Bean 类
       Map<Class<?>, List<Object>> beanMap = BeanContainer.getClassMap();
       for (Map.Entry<Class<?>, List<Object>> beanEntry : beanMap.entrySet()) {
           // 获取 Bean 类与 Bean 实例
           Class<?> beanClass = beanEntry.getKey();
           Object beanInstance = beanEntry.getValue().get(0);
           // 获取 Bean 类中所有的字段
           List<String>beanFields = BeanPropertyUtil.getAllPropertyName(beanClass);
           if (beanFields!=null&&beanFields.size()>0) {
               // 遍历所有的 Bean 字段
               for (String propertyName : beanFields) {
            	   Field beanField=BeanPropertyUtil.getField(beanClass, propertyName);
                   // 判断当前 Bean 字段是否带有 @Inject 注解
                   if (beanField!=null&&beanField.isAnnotationPresent(Inject.class)) {
                	   Object implementInstance =null;
                	  String beanId=  beanField.getAnnotation(Inject.class).value();
                	  if(beanId!=null&&!beanId.isEmpty())
                	  {
                		  implementInstance=BeanContainer.getBean(beanId);
                	  }
                	  else{
                       // 获取 Bean 字段对应的接口
                       Class<?> interfaceClass = beanField.getType();
                       // 获取 Bean 字段对应的实现类
                       Class<?> implementClass = findImplementClass(interfaceClass);
                       // 若存在实现类，则执行以下代码
                       if (implementClass != null) {
                       	// 从 Bean Map 中获取该实现类对应的实现类实例
                           implementInstance = BeanContainer.getBean(implementClass);
                       }
                	  }
                	 
                     // 设置该 Bean 字段的值
                     if (implementInstance != null) {
                         try {
                        	 BeanPropertyUtil.setPropertyValue(beanInstance, propertyName, implementInstance);
						} catch (Exception e) {
							throw new JSimpleException(e);
						}
                     }
                   }
               }
           }
       }   
   }
   
   private static Class<?> findImplementClass(Class<?> interfaceClass) {
       Class<?> implementClass = interfaceClass;
       // 判断接口上是否标注了 @Impl 注解
       if (interfaceClass.isAnnotationPresent(Impl.class)) {
           // 获取强制指定的实现类
           implementClass = interfaceClass.getAnnotation(Impl.class).value();
       } else {
           // 获取该接口所有的实现类
           List<Class<?>> implementClassList = ClassScaner.getClassListBySuper(interfaceClass);
           if (CollectionUtil.isNotEmpty(implementClassList)) {
               // 获取第一个实现类
               implementClass = implementClassList.get(0);
           }
       }
       // 返回实现类对象
       return implementClass;
   }
}
