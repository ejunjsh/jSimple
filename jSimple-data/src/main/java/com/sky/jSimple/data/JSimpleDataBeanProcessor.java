package com.sky.jSimple.data;

import com.sky.jSimple.aop.AOPFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.data.annotation.GetCount;
import com.sky.jSimple.data.annotation.GetEntity;
import com.sky.jSimple.utils.BeanPropertyUtil;
import org.apache.commons.dbutils.BeanProcessor;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSimpleDataBeanProcessor extends BeanProcessor {

    private SessionFactory sessionFactory;

    public JSimpleDataBeanProcessor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public JSimpleDataBeanProcessor(SessionFactory sessionFactory, Map<String, String> columnToPropertyOverrides) {
        super(columnToPropertyOverrides);
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected <T> T newInstance(Class<T> cls) throws SQLException {
        List<String> propertyNames = BeanPropertyUtil.getAllPropertyName(cls);
        boolean flag = false;
        for (String propertyName : propertyNames) {
            Field field = BeanPropertyUtil.getField(cls, propertyName);
            if (field != null && (field.isAnnotationPresent(GetEntity.class)
                    || field.isAnnotationPresent(GetCount.class))) {
                flag = true;
                break;
            }
        }
        List<Proxy> proxies = new ArrayList<Proxy>();
        if (flag) {
            Object proxy = new JSimpleDataBeanProxy(sessionFactory);
            BeanContainer.setBean(JSimpleDataBeanProxy.class, proxy);
            proxies.add((Proxy) proxy);
        }
        return AOPFactory.createEnhanceObject(cls, proxies);
    }
}
