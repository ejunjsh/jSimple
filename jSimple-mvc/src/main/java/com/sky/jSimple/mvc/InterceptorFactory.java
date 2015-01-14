package com.sky.jSimple.mvc;

import com.sky.jSimple.aop.IProxyFactory;
import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.mvc.annotation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InterceptorFactory implements IProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorFactory.class);

    private List<Class<?>> clsClasses;

    private List<Class<?>> interceptorClasses;

    public InterceptorFactory() {
        clsClasses = ClassScaner.getClassListBySuper(IController.class);
        interceptorClasses = ClassScaner.getClassListBySuper(Interceptor.class);

        Collections.sort(interceptorClasses, new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> o1, Class<?> o2) {
                if (o1.getAnnotation(Order.class).value() > o2.getAnnotation(Order.class).value()) {
                    return -1;
                } else if (o1.getAnnotation(Order.class).value() < o2.getAnnotation(Order.class).value()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    public List<Proxy> create(Class<?> cls) {

        if (clsClasses.contains(cls)) {

            List<Proxy> proxyList = new ArrayList<Proxy>();
            for (Class<?> inerceptorClass : interceptorClasses) {
                try {
                    if (BeanContainer.getBean(inerceptorClass) == null) {
                        Object proxy = inerceptorClass.newInstance();
                        BeanContainer.setBean(inerceptorClass, proxy);
                        proxyList.add((Proxy) proxy);
                    } else {
                        proxyList.add((Proxy) BeanContainer.getBean(inerceptorClass));
                    }


                } catch (InstantiationException e) {
                    logger.debug("[jSimple]--create proxy error");
                    throw new JSimpleException(e);

                } catch (IllegalAccessException e) {
                    logger.debug("[jSimple]--create proxy error");
                    throw new JSimpleException(e);
                }
            }
            return proxyList;
        }
        return null;

    }
}
