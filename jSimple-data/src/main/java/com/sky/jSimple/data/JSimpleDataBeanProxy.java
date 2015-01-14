package com.sky.jSimple.data;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.data.annotation.GetBy;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.BeanPropertyUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSimpleDataBeanProxy implements Proxy {

    private SessionFactory sessionFactory;

    public JSimpleDataBeanProxy(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        try {
            Method targetMethod = proxyChain.getTargetMethod();
            Object targetObject = proxyChain.getTargetObject();

            String propertyName = targetMethod.getName().replace("get", "");
            String firstLetter = propertyName.substring(0, 1).toLowerCase();
            propertyName = firstLetter + propertyName.substring(1);
            Field field = BeanPropertyUtil.getField(proxyChain.getTargetClass(), propertyName);

            if (field != null && field.isAnnotationPresent(GetBy.class)) {
                field.setAccessible(true);
                Object propertyValue = field.get(targetObject);
                //already getBy...
                if (propertyValue == null) {
                    String condition = field.getAnnotation(GetBy.class).condition();
                    String values = field.getAnnotation(GetBy.class).values();

                    String[] ss = values.split(",");
                    List<Object> params = new ArrayList<Object>();
                    for (String s : ss) {
                        params.add(BeanPropertyUtil.getPropertyValue(targetObject, s));
                    }

                    Class<?> returnClass = null;
                    Type returntType = targetMethod.getGenericReturnType();
                    if (returntType instanceof ParameterizedType) {
                        returnClass = (Class<?>) ((ParameterizedType) returntType).getActualTypeArguments()[0];
                        Session session = sessionFactory.getSession();

                        String sql = SQLHelper.generateSelectSQL(returnClass, condition, "");
                        Object value = DBHelper.queryBeanList(session, returnClass, sql, params.toArray());
                        BeanPropertyUtil.setPropertyValue(targetObject, propertyName, value);
                    } else {
                        returnClass = targetMethod.getReturnType();

                        Session session = sessionFactory.getSession();
                        String sql = SQLHelper.generateSelectSQL(returnClass, condition, "");
                        Object value = DBHelper.queryBean(session, returnClass, sql, params.toArray());
                        BeanPropertyUtil.setPropertyValue(targetObject, propertyName, value);
                        session.close();

                    }
                }
            }
            result = proxyChain.doProxyChain();
        } catch (Exception e) {
            throw new JSimpleException(e);
        }
        return result;
    }

}
