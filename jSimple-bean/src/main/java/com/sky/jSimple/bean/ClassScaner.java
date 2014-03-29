package com.sky.jSimple.bean;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Properties;

import com.sky.jSimple.config.jSimpleConfig;
import com.sky.jSimple.utils.ClassUtil;
import com.sky.jSimple.utils.PropsUtil;

public class ClassScaner {

	private static final String packageName = jSimpleConfig.getConfigString("app.package");

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
