package com.sky.jSimple.blog.entity;

import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shaojunjie on 2015/1/12.
 */
@Entity("comment")
public class Comment implements Serializable {
    @Id
    private long id;
    private String content;
    private Date createdDate;
    private Date lastModifiedDate;
    private long uid;
    private long blogId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }
}
