package com.sky.jSimple.blog.model;

import java.util.List;

public class BlogModel {
   private int id;
   
   private TagModel tagModel;
   
   private List<TagModel> tags;
   

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public TagModel getTagModel() {
	return tagModel;
}

public void setTagModel(TagModel tagModel) {
	this.tagModel = tagModel;
}

public List<TagModel> getTags() {
	return tags;
}

public void setTags(List<TagModel> tags) {
	this.tags = tags;
}

}
