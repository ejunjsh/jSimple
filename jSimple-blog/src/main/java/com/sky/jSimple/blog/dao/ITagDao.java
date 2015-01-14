package com.sky.jSimple.blog.dao;

import com.sky.jSimple.blog.entity.Tag;

import java.util.List;

public interface ITagDao {
    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    Tag getById(Long id);

    List<Tag> getPager(int pageNumber, int pageSize, String condition, String sort, Object... params);

    long getCount(String condition, Object... params);

    Tag getByName(String name);

    Tag getByLinkName(String linkName);

    List<Tag> getAll(String sort);
}
