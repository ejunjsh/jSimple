package com.sky.jSimple.data;

import com.sky.jSimple.data.annotation.Column;
import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.Id;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.BeanPropertyUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class JSimpleDataTemplate {

    private SessionFactory sessionFactory;
    private String dbType;

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void insert(Object entity) {
        Session session = sessionFactory.getSession();

        Map<String, String> entityMap = EntityHelper.getEntityMap().get(entity.getClass().getAnnotation(Entity.class).value());
        String sql = SQLHelper.generateInsertSQL(entity.getClass(), entityMap.keySet());
        long id = (Long) DBHelper.insertReturnPK(session, sql, EntityHelper.entityToArray(entity));
        try {
            List<String> fields = BeanPropertyUtil.getAllPropertyName(entity.getClass());
            for (String s : fields) {
                Field field = BeanPropertyUtil.getField(entity.getClass(), s);
                if (field.isAnnotationPresent(Id.class)) {
                    BeanPropertyUtil.setPropertyValue(entity, s, id);
                }
                break;
            }

        } catch (Exception e) {
            throw new JSimpleException(e);
        }
    }

    public void update(Object entity) {
        Session session = sessionFactory.getSession();
        String idCondition = "=?";
        Map<String, String> entityMap = EntityHelper.getEntityMap().get(entity.getClass().getAnnotation(Entity.class).value());
        List<Object> objects = EntityHelper.entityToList(entity);
        for (String s : entityMap.values()) {
            Field field;
            try {
                field = BeanPropertyUtil.getField(entity.getClass(), s);
                if (field.isAnnotationPresent(Id.class)) {
                    objects.add(BeanPropertyUtil.getPropertyValue(entity, s));

                    String columnName = "";
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        columnName = column.value();
                    }

                    if (columnName != null && !columnName.isEmpty()) {
                        idCondition = columnName + idCondition;
                    } else {
                        idCondition = field.getName() + idCondition;
                    }
                    break;
                }
            } catch (Exception e) {
                throw new JSimpleException(e);
            }

        }

        String sql = SQLHelper.generateUpdateSQL(entity.getClass(), entityMap.keySet(), idCondition);

        DBHelper.update(session, sql, objects.toArray());
    }

    public void delete(Object id, Class<?> entityClass) {

        Session session = sessionFactory.getSession();

        String idCondition = "=?";
        Map<String, String> entityMap = EntityHelper.getEntityMap().get(entityClass.getAnnotation(Entity.class).value());
        for (String s : entityMap.values()) {
            Field field;
            try {
                field = BeanPropertyUtil.getField(entityClass, s);
                if (field.isAnnotationPresent(Id.class)) {
                    String columnName = "";
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        columnName = column.value();
                    }

                    if (columnName != null && !columnName.isEmpty()) {
                        idCondition = columnName + idCondition;
                    } else {
                        idCondition = field.getName() + idCondition;
                    }
                    break;
                }
            } catch (Exception e) {
                throw new JSimpleException(e);
            }

        }

        String sql = SQLHelper.generateDeleteSQL(entityClass, idCondition);
        DBHelper.update(session, sql, id);
    }

    public <T> T querySingleByCondition(Class<T> cls, String condition, Object... params) {
        Session session = sessionFactory.getSession();

        String sql = SQLHelper.generateSelectSQL(cls, condition, "");
        return DBHelper.queryBean(session, cls, sql, params);
    }

    public <T> List<T> queryListByCondition(Class<T> cls, String condition, String sort, Object... params) {
        Session session = sessionFactory.getSession();

        String sql = SQLHelper.generateSelectSQL(cls, condition, sort);
        return DBHelper.queryBeanList(session, cls, sql, params);
    }

    public <T> List<T> queryList(Class<T> cls, String sql, Object... params) {
        Session session = sessionFactory.getSession();
        return DBHelper.queryBeanList(session, cls, sql, params);
    }

    public <T> T querySingle(Class<T> cls, String sql, Object... params) {
        Session session = sessionFactory.getSession();

        return DBHelper.queryBean(session, cls, sql, params);
    }

    public <T> T getById(Class<T> cls, Long id) {
        Session session = sessionFactory.getSession();

        String idCondition = "=?";
        Map<String, String> entityMap = EntityHelper.getEntityMap().get(cls.getAnnotation(Entity.class).value());
        for (String s : entityMap.values()) {
            Field field;
            try {
                field = BeanPropertyUtil.getField(cls, s);
                if (field.isAnnotationPresent(Id.class)) {
                    String columnName = "";
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        columnName = column.value();
                    }

                    if (columnName != null && !columnName.isEmpty()) {
                        idCondition = columnName + idCondition;
                    } else {
                        idCondition = field.getName() + idCondition;
                    }
                    break;
                }
            } catch (Exception e) {
                throw new JSimpleException(e);
            }

        }

        String sql = SQLHelper.generateSelectSQL(cls, idCondition, "");
        return DBHelper.queryBean(session, cls, sql, id);
    }

    public <T> List<T> getPager(Class<T> cls, int pageNumber, int pageSize, String condition, String sort, Object... params) {
        Session session = sessionFactory.getSession();

        String sql = SQLHelper.generateSelectSQLForPager(pageNumber, pageSize, cls, condition, sort, dbType);
        return DBHelper.queryBeanList(session, cls, sql, params);
    }

    public long getCount(String condition, Class<?> entityClass, Object... params) {
        Session session = sessionFactory.getSession();

        String sql = SQLHelper.generateSelectSQLForCount(entityClass, condition);
        return DBHelper.queryCount(session, sql, params);
    }

    public ResultSet query(String sql, Object... params) {
        return DBHelper.query(sessionFactory.getSession(), sql, params);
    }

    public int execute(String sql, Object... params) {
        return DBHelper.execute(sessionFactory.getSession(), sql, params);
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
