package com.sky.jSimple.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanPropertyUtil {
	
	 //cache map
     private static Map<Class<?>, Map<String,PropertyDescriptor>> propertyMap;
     static
     {
    	 propertyMap=new HashMap<Class<?>, Map<String,PropertyDescriptor>>();
     }
     
     private static void extractProperty(Class<?> cls)
     {
    	 try {
			BeanInfo beanInfo=Introspector.getBeanInfo(cls);
			PropertyDescriptor[] descriptors=beanInfo.getPropertyDescriptors();
			Map<String,PropertyDescriptor> map=new HashMap<String, PropertyDescriptor>();
			for(PropertyDescriptor descriptor:descriptors)
			{
				//ignore the class property.
				if(!descriptor.getName().equals("class"))
				map.put(descriptor.getName(), descriptor);
			}
			propertyMap.put(cls, map);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
    	 
     }
     
     public static void setPropertyValue(Object o,String propertyName,Object value)
     {
    	  Map<String ,PropertyDescriptor> map=propertyMap.get(o.getClass());
    	  if(map==null)
    	  {
    		  extractProperty(o.getClass());
    	  }
    	  map=propertyMap.get(o.getClass());
    	  if(map!=null)
    	  {
    		    PropertyDescriptor descriptor=map.get(propertyName);
    		    try {
					descriptor.getWriteMethod().invoke(o, value);
				} catch (Exception e) {
					e.printStackTrace();
				} 
    	  }
     }
     
     public static Object getPropertyValue(Object o,String propertyName)
     {
    	  Map<String ,PropertyDescriptor> map=propertyMap.get(o.getClass());
    	  if(map==null)
    	  {
    		  extractProperty(o.getClass());
    	  }
    	  map=propertyMap.get(o.getClass());
    	  if(map!=null)
    	  {
    		    PropertyDescriptor descriptor=map.get(propertyName);
    		    try {
					return descriptor.getReadMethod().invoke(o, null);
				} catch (Exception e) {
					e.printStackTrace();
				} 
    	  }
    	  return null;
     }
     
  
     public static List<String> getAllPropertyName(Class<?> cls)
     {
    	  Map<String ,PropertyDescriptor> map=propertyMap.get(cls);
    	  if(map==null)
    	  {
    		  extractProperty(cls);
    	  }
    	  map=propertyMap.get(cls);
    	  if(map!=null)
    	  {
    		  List<String> ss=new ArrayList<String>();
    		  for(String s: map.keySet())
    		  {
    			  ss.add(s);
    		  }
    		  return ss;
    	  }
		return null;
     }
     
     public static PropertyDescriptor getDescriptor(Class<?> cls,String propertyName)
     {
    	 Map<String ,PropertyDescriptor> map=propertyMap.get(cls);
	   	  if(map==null)
	   	  {
	   		  extractProperty(cls);
	   	  }
	   	  map=propertyMap.get(cls);
	   	  if(map!=null)
	   	  {
	   	      return map.get(propertyName);
	   	  }
		  return null;
     }
     
     //can get field from parent side.
     public static Field getField(Class<?> cls,String propertyName)
     {
    	   Field field = null;
			try {
				field = cls.getDeclaredField(propertyName);
			} catch (Exception e) {
				//e.printStackTrace();
			} 
    	   if(field!=null)
    	   {
    		   return field;
    	   }
    	   else {
			if(cls.getSuperclass()!=null)
			{
				return getField(cls.getSuperclass(), propertyName);
			}
			return null;
		}
     }
     
}
