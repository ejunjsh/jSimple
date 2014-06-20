package com.sky.jSimple.bean;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.Loader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.CastUtil;

public class BeanContainer {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanContainer.class);
	
	private static BeanContainer instance;
	
	static
	{
		instance=new BeanContainer();
		instance.classContainer=new HashMap<Class<?>, Object>(50);
		instance.nameContainer=new HashMap<String, Object>(50);
		loadBeansFromXml();
	}
	
	
	private  Map<Class<?>,Object> classContainer;
	
	private  Map<String, Object> nameContainer;
	
	
    
    @SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
        if (!instance.classContainer.containsKey(cls)) {
            return null;
        }
        return (T) instance.classContainer.get(cls);
    }
    
    @SuppressWarnings("unchecked")
   	public static <T> T getBean(String name) {
           if (!instance.nameContainer.containsKey(name)) {
               return null;
           }
           return (T) instance.nameContainer.get(name);
       }

    public static void setBean(Class<?> cls, Object obj) {
    	
    	instance.classContainer.put(cls, obj);
    }
    
    public static void setBean(String name, Object obj) {
    	
    	instance.nameContainer.put(name, obj);
        Class<?> cls=obj.getClass();
        if(getBean(cls)==null)
        setBean(cls, obj);
    }
    
    public static Map<Class<?>,Object> getClassMap()
    {
    	
    	return instance.classContainer;
    }
    
    public static Map<String,Object> getNameMap()
    {
    	return instance.nameContainer;
    }
    
    
    public static void loadBeansFromXml()
    {
       InputStream is= BeanContainer.class.getClassLoader().getResourceAsStream("/beans.xml");
       SAXReader reader = new SAXReader();
       try {
		Document document = reader.read(is);
		Element rootElement=document.getRootElement();
		List<Element> beanElements= rootElement.elements("bean");
		for(Element beanElement: beanElements)
		{
			String idsString=beanElement.attributeValue("id");
			String classString=beanElement.attributeValue("class");
			Object bean=BeanAssembly.assemble(idsString,Class.forName(classString));
			List<Element> propertyElements =beanElement.elements("property");
			if(propertyElements!=null&&propertyElements.size()>0)
			{
				for(Element propertyElement: propertyElements)
				{
					String refString=propertyElement.attributeValue("ref");
					String namesString=propertyElement.attributeValue("name");
					String valueString=propertyElement.attributeValue("value");
					if(refString==null)
					{
						if(namesString!=null&&!namesString.isEmpty()&&valueString!=null&&!valueString.isEmpty())
						{
							Field field= bean.getClass().getDeclaredField(namesString);
							field.setAccessible(true);
							Object o=CastUtil.castPirmitiveObject(field.getType(), valueString);
							field.set(bean, o);
						}
					}
					else {
						if(namesString!=null&&!namesString.isEmpty())
						{
							Object refBean=BeanContainer.getBean(refString);
							if(refBean!=null)
							{
								Field field= bean.getClass().getDeclaredField(namesString);
								field.setAccessible(true);
								field.set(bean,refBean);
							}
						}
					}
				}
			}
			BeanContainer.setBean(idsString, bean);
		}
	} catch (Exception e) {
		e.printStackTrace();
	   }
    }
}
