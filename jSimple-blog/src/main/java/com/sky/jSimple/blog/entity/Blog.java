package com.sky.jSimple.blog.entity;

import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.data.annotation.GetCount;
import com.sky.jSimple.data.annotation.GetEntity;
import com.sky.jSimple.data.annotation.Id;
import com.sky.jSimple.utils.DateUtil;
import com.sky.jSimple.utils.StringUtil;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

@Entity("blog")
public class Blog implements Serializable {

    /**
     * @Fields serialVersionUID : description
     */
    private static final long serialVersionUID = 9196895526224939131L;

    @Id
    private long id;
    private String title;
    private String content;
    private Date createdDate;
    private Date lastModifiedDate;
    private long uid;
    private long viewCount;
    private String linkName;
    private long categoryId;
    private String tags;

    private int isRecommend;

    @GetEntity(condition = "id=?", values = "categoryId")
    private Category category;

    @GetEntity(condition = "id=?", values = "uid")
    private User user;

    @GetCount(condition = "blogId=?", values = "id", cls = Comment.class)
    private Long commentCount;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedDateF() {
        return DateFormatUtils.format(createdDate, "yyyy-MM-dd HH:mm:ss");
    }

    public String getCreatedMonth() {
        return DateFormatUtils.format(createdDate, "MM");
    }

    public String getCreatedDay() {
        return DateFormatUtils.format(createdDate, "dd");
    }

    public String getShortContent() {
        return StringUtil.truncateHTML(content, 800);
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public String getContentNoHtml() {
        return StringUtil.delHTMLTag(content);
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getRelativeDate() {
        return DateUtil.RelativeDateFormat.format(this.createdDate);
    }
}
