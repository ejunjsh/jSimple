package com.sky.jSimple.blog.entity;

import java.io.Serializable;
import java.util.Date;

import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.Id;

@Entity("user")
public class User implements Serializable {
	
	/** 
	* @Fields serialVersionUID : description
	*/ 
	private static final long serialVersionUID = 524192755676586019L;
	@Id
	private long id ;
	private String email;
    private String  nickName;
	private String   pwd;
	private Date   createdDate;
	private Date    lastLoginDate ;
	private String avatar;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
