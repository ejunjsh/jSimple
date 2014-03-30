package com.sky.jSimple.blog.entity;

import java.util.Date;

import com.sky.jSimple.data.annotation.Entity;

@Entity("category")
public class Category {
	private long id; 
	private String name; 
	private Date createdDate; 
	private Date lastModifiedDate; 
	private long uid;
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
}
