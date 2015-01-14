package com.sky.jSimple.blog.dao;

import com.sky.jSimple.data.JSimpleDataTemplate;
import com.sky.jSimple.ioc.annotation.Inject;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基类Dao，封装了增删查改
 *
 * @param <T>
 */
public class BaseDao<T> {

    private Class<T> entityClass;
    @Inject
    private JSimpleDataTemplate jSimpleDataTemplate;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void insert(T entity) {
        jSimpleDataTemplate.insert(entity);
    }


    public void update(T entity) {
        jSimpleDataTemplate.update(entity);
    }

    public void delete(Long id) {
        jSimpleDataTemplate.delete(id, entityClass);
    }

    public T getById(Long id) {
        return jSimpleDataTemplate.getById(entityClass, id);
    }


    public List<T> getPager(int pageNumber, int pageSize, String condition, String sort, Object... params) {
        return jSimpleDataTemplate.getPager(entityClass, pageNumber, pageSize, condition, sort, params);
    }

    public long getCount(String condition, Object... params) {
        return jSimpleDataTemplate.getCount(condition, entityClass, params);
    }

    public List<T> getAll(String sort) {
        return jSimpleDataTemplate.queryListByCondition(entityClass, "", sort);
    }


    public JSimpleDataTemplate getjSimpleDataTemplate() {
        return jSimpleDataTemplate;
    }


    public void setjSimpleDataTemplate(JSimpleDataTemplate jSimpleDataTemplate) {
        this.jSimpleDataTemplate = jSimpleDataTemplate;
    }
}
