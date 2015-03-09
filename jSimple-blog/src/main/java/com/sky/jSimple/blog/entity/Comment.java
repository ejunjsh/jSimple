package com.sky.jSimple.blog.entity;

import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.GetEntity;
import com.sky.jSimple.data.annotation.Id;
import com.sky.jSimple.utils.DateUtil;
import org.apache.commons.lang.time.DateFormatUtils;

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
    private String nickName;
    private String email;
    private String website;

    @GetEntity(condition = "id=?", values = "uid")
    private User user;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCreatedDateF() {
        return DateFormatUtils.format(createdDate, "yyyy年MM月dd日 HH:mm:ss");
    }


    public String getRelativeDate() {
        return DateUtil.RelativeDateFormat.format(this.createdDate);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
