package com.sky.jSimple.bean;

import com.sky.jSimple.config.jSimpleConfig;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.BeanPropertyUtil;
import com.sky.jSimple.utils.CastUtil;
import com.sky.jSimple.utils.StringUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanContainer {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanContainer.class);
	
	private static BeanContainer instance;
	
	static
	{
		instance=new BeanContainer();
		instance.classContainer=new HashMap<Class<?>, List<Object>>(50);
		instance.nameContainer=new HashMap<String, Object>(50);
	}
	
	
	private  Map<Class<?>,List<Object>> classContainer;
	
	private  Map<String, Object> nameContainer;
	
	
    
    @SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
        if (!instance.classContainer.containsKey(cls)) {
            return null;
        }
        List<Object> objecList=instance.classContainer.get(cls);
        if(objecList!=null&&objecList.size()>0)
        {
        	return  (T)objecList.get(0);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> getBeans(Class<T> cls) {
        if (!instance.classContainer.containsKey(cls)) {
            return null;
        }
        List<Object> objecList=instance.classContainer.get(cls);
        if(objecList!=null&&objecList.size()>0)
        {
        	return  (List<T>)objecList;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
   	public static <T> T getBean(String name) {
           if (!instance.nameContainer.containsKey(name)) {
               return null;
           }
           return (T) instance.nameContainer.get(name);
       }

    public static void setBean(Class<?> cls, Object obj) {
    	List<Object> objects=instance.classContainer.get(cls);
    	if(objects==null)
    	{
    		objects=new ArrayList<Object>();
    	}
    	objects.add(obj);
    	instance.classContainer.put(cls, objects);
    }
    
    public static void setBean(String name, Object obj) {
    	
    	instance.nameContainer.put(name, obj);
        Class<?> cls=obj.getClass();
        setBean(cls, obj);
    }
    
    public static Map<Class<?>,List<Object>> getClassMap()
    {
    	
    	return instance.classContainer;
    }
    
    public static Map<String,Object> getNameMap()
    {
    	return instance.nameContainer;
    }
    
    
    public static void loadBeansFromXml() throws JSimpleException
    {
       InputStream is= BeanContainer.class.getClassLoader().getResourceAsStream(jSimpleConfig.configFilePath);
       SAXReader reader = new SAXReader();
       try {
		Document document = reader.read(is);
		Element rootElement=document.getRootElement();
        Element beansElement=rootElement.element("beans");
        String scanPackage=beansElement.attributeValue("scan-package");
        if(!StringUtil.isEmpty(scanPackage))
        {
            jSimpleConfig.scanPackage=scanPackage;
        }
        else
        {
            throw new JSimpleException("please configure the scan-package property of beans node in your config file");
        }
		List<Element> beanElements= beansElement.elements("bean");
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
							PropertyDescriptor descriptor= BeanPropertyUtil.getDescriptor(bean.getClass(), namesString);
							Object o=CastUtil.castPirmitiveObject(descriptor.getPropertyType(), valueString);
							BeanPropertyUtil.setPropertyValue(bean, namesString, o);
						}
					}
					else {
						if(namesString!=null&&!namesString.isEmpty())
						{
							Object refBean=BeanContainer.getBean(refString);
							if(refBean!=null)
							{
								BeanPropertyUtil.setPropertyValue(bean, namesString, refBean);
							}
						}
					}
				}
			}
			BeanContainer.setBean(idsString, bean);
		}
	} catch (Exception e) {
		throw new JSimpleException(e);
	   }
    }
}
