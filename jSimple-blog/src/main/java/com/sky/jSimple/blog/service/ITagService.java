package com.sky.jSimple.blog.service;

import com.sky.jSimple.blog.entity.Tag;

import java.util.List;

public interface ITagService {
    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    Tag getById(Long id);


    Tag getByLinkName(String linkName);

    List<Tag> getAllTags(String sortBy, boolean isDesc);
}
