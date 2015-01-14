package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Tag;
import com.sky.jSimple.blog.service.ITagService;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPut;

import java.util.Date;
import java.util.List;

@Bean
public class TagController extends BaseController {

    @Inject
    private ITagService tagService;


    @HttpGet("/api/tag")
    public ActionResult getTags() {
        List<Tag> tags = tagService.getAllTags("name", true);
        return json(tags);
    }

    @HttpGet("/api/tag/{linkName}")
    public ActionResult getTagByLinkName(String linkName) {
        Tag tag = tagService.getByLinkName(linkName);
        return json(tag);
    }

    @HttpPut("/api/tag")
    public ActionResult updateTag(Tag tag) {
        Tag t = tagService.getById(tag.getId());
        t.setLinkName(tag.getLinkName());
        t.setLastModifiedDate(new Date());
        tagService.update(t);
        return json(tag);
    }

    public ITagService getTagService() {
        return tagService;
    }

    public void setTagService(ITagService tagService) {
        this.tagService = tagService;
    }


}
