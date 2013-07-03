package com.sky.jSimple.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectUtil {


    // 设置成员变量
    public static void setField(Object obj, String fieldName, Object fieldValue) {
        try {
            if (PropertyUtils.isWriteable(obj, fieldName)) {
                PropertyUtils.setProperty(obj, fieldName, fieldValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 获取成员变量
    public static Object getFieldValue(Object obj, String fieldName) {
        Object propertyValue = null;
        try {
            if (PropertyUtils.isReadable(obj, fieldName)) {
                propertyValue = PropertyUtils.getProperty(obj, fieldName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return propertyValue;
    }

    // 复制所有成员变量
    public static void copyFields(Object source, Object target) {
        try {
            for (Field field : source.getClass().getDeclaredFields()) {
                // 若不为 static 成员变量，则进行复制操作
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true); // 可操作私有成员变量
                    field.set(target, field.get(source));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 通过反射创建实例
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        T instance;
        try {
            Class<?> commandClass = ClassUtil.loadClass(className, true);
            instance = (T) commandClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }
}
