package com.sky.jSimple.blog.entity;

import java.util.Date;

import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.Id;

@Entity("category")
public class Category {
	
	@Id
	private long id; 
	private String name; 
	private Date createdDate; 
	private Date lastModifiedDate; 
	private long uid;
	private String linkName;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	} 
}
