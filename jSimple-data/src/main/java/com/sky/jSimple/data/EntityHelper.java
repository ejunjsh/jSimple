package com.sky.jSimple.data;

import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.data.annotation.*;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.BeanPropertyUtil;
import com.sky.jSimple.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class EntityHelper {

    private static final Logger logger = LoggerFactory.getLogger(EntityHelper.class);
    private static final Map<String, Map<String, String>> entityMap = new HashMap<String, Map<String, String>>(); // Entity 类 => (列名 => 字段名)

    static {
        try {


            // 获取并遍历所有 Entity 类
            List<Class<?>> entityClassList = ClassScaner.getClassListByAnnotation(Entity.class);
            for (Class<?> entityClass : entityClassList) {
                // 获取并遍历该 Entity 类中所有的字段
                List<String> fields = BeanPropertyUtil.getAllPropertyName(entityClass);
                if (fields != null && fields.size() > 0) {
                    // 创建一个 Field Map（用于存放列名与字段名的映射关系）
                    Map<String, String> fieldMap = new LinkedHashMap<String, String>();
                    for (String s : fields) {
                        Field field = BeanPropertyUtil.getField(entityClass, s);
                        if (field != null) {
                            String fieldName = field.getName();
                            String columnName;
                            if (!field.isAnnotationPresent(Ignore.class) && !field.isAnnotationPresent(GetEntity.class) && !field.isAnnotationPresent(GetCount.class)) {
                                // 若该字段上存在 @Column 注解，则优先获取注解中的列名
                                if (field.isAnnotationPresent(Column.class)) {
                                    columnName = field.getAnnotation(Column.class).value();
                                } else {
                                    columnName = fieldName;
                                }
                                fieldMap.put(columnName, fieldName);
                            }
                            // 若字段名与列名不同，则需要进行映射
//                    if (!fieldName.equals(columnName)) {

//                    }
                        }
                    }
                    // 将 Entity 类与 Field Map 放入 Entity Map 中
                    if (MapUtil.isNotEmpty(fieldMap)) {
                        String key = entityClass.getAnnotation(Entity.class).value();
                        entityMap.put(key, fieldMap);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Map<String, String>> getEntityMap() {
        return entityMap;
    }

    public static Object[] entityToArray(Object entity) {
        List<String> fields = BeanPropertyUtil.getAllPropertyName(entity.getClass());
        List<Object> objects = new ArrayList<Object>();
        for (int i = 0; i < fields.size(); i++) {
            Field field = BeanPropertyUtil.getField(entity.getClass(), fields.get(i));
            try {
                if (field != null && !field.isAnnotationPresent(Ignore.class) && !field.isAnnotationPresent(GetEntity.class) && !field.isAnnotationPresent(GetCount.class)) {
                    objects.add(BeanPropertyUtil.getPropertyValue(entity, fields.get(i)));
                }
            } catch (Exception e) {
                throw new JSimpleException(e);
            }
        }
        return objects.toArray();
    }

    public static List<Object> entityToList(Object entity) {
        List<String> fields = BeanPropertyUtil.getAllPropertyName(entity.getClass());
        List<Object> objects = new ArrayList<Object>();
        for (int i = 0; i < fields.size(); i++) {
            Field field = BeanPropertyUtil.getField(entity.getClass(), fields.get(i));
            try {
                if (field != null && !field.isAnnotationPresent(Ignore.class) && !field.isAnnotationPresent(GetEntity.class) && !field.isAnnotationPresent(GetCount.class)) {
                    objects.add(BeanPropertyUtil.getPropertyValue(entity, fields.get(i)));
                }
            } catch (Exception e) {
                throw new JSimpleException(e);
            }
        }
        return objects;
    }
}
