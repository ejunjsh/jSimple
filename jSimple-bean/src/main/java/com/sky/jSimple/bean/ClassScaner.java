package com.sky.jSimple.bean;

import com.sky.jSimple.config.jSimpleConfig;
import com.sky.jSimple.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;

public class ClassScaner {

	private static final Logger logger = LoggerFactory.getLogger(ClassScaner.class);
	
	private static final String packageName = jSimpleConfig.scanPackage;

    public static List<Class<?>> getClassList() {
        return ClassUtil.getClassList(packageName, true);
    }

    public static List<Class<?>> getClassListBySuper(Class<?> superClass) {
        return ClassUtil.getClassListBySuper(packageName, superClass);
    }

    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
        return ClassUtil.getClassListByAnnotation(packageName, annotationClass);
    }


}
