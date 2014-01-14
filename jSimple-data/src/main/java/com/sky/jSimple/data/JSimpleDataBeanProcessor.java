package com.sky.jSimple.data;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BeanProcessor;
import com.sky.jSimple.aop.AOPFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.data.annotation.GetBy;

public class JSimpleDataBeanProcessor extends BeanProcessor {

	private SessionFactory sessionFactory;
	
	public JSimpleDataBeanProcessor(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}
	
    public JSimpleDataBeanProcessor(SessionFactory sessionFactory,Map<String,String> columnToPropertyOverrides)
	{
		super(columnToPropertyOverrides);
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	 protected <T> T newInstance(Class<T> cls) throws SQLException {
        Method[] methods =cls.getDeclaredMethods();
		boolean flag=false;
		for(Method method :methods)
		{
			if(method.isAnnotationPresent(GetBy.class))
			{
				flag=true;
				break;
			}
		}
		List<Proxy> proxies=new ArrayList<Proxy>();
		if(flag)
		{
				Object proxy=new JSimpleDataBeanProxy(sessionFactory);
				BeanContainer.setBean(JSimpleDataBeanProxy.class, proxy);
				proxies.add((Proxy)proxy);
		}
		return AOPFactory.createEnhanceObject(cls, proxies);
    }
}
