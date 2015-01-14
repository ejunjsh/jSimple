package com.sky.jSimple.blog.service;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ITagDao;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.cache.annotation.Cache;
import com.sky.jSimple.cache.annotation.Evict;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

import java.util.List;

@Bean
public class TagService implements ITagService {

    @Inject
    private ITagDao tagDao;

    @Transactional
    @Evict(scope = "tag1")
    public void insert(Tag tag) {
        tagDao.insert(tag);
    }

    @Transactional
    @Evict(scope = "tag1")
    public void update(Tag tag) {
        tagDao.update(tag);
    }

    @Transactional
    public void delete(Long id) {
        tagDao.delete(id);
    }

    public Tag getById(Long id) {
        return tagDao.getById(id);
    }

    public Tag getByLinkName(String linkName) {
        return tagDao.getByLinkName(linkName);
    }

    @Override
    @Cache(scope = "tag1")
    public List<Tag> getAllTags(String sortBy, boolean isDesc) {
        String sort = sortBy + " " + (isDesc ? "desc" : "asc");
        return tagDao.getAll(sort);
    }

    public ITagDao getTagDao() {
        return tagDao;
    }

    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }

}
