package com.sky.jSimple.blog.service;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.dao.ICommentDao;
import com.sky.jSimple.blog.entity.Comment;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.ioc.annotation.Inject;

/**
 * Created by shaojunjie on 2015/1/12.
 */

@Bean
public class CommentService implements ICommentService {

    @Inject
    private ICommentDao commentDao;


    @Transactional
    @Override
    public void insert(Comment comment) {
        commentDao.insert(comment);
    }

    @Transactional
    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        commentDao.delete(id);
    }

    @Override
    public Comment getById(Long id) {
        return commentDao.getById(id);
    }

    @Override
    public Pagination<Comment> getByBlogId(int pageNumber, int pageSize, long blogId, String sortBy, boolean isDesc) {

        Pagination pagination = new Pagination();

        String sort = sortBy + " " + (isDesc ? "desc" : "asc");

        pagination.setData(commentDao.getPager(pageNumber, pageSize, "blogId=?", sort, blogId));
        pagination.setRecordCount(commentDao.getCount("blogId=?", blogId));
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNumber);
        return pagination;

    }

    public ICommentDao getCommentDao() {
        return commentDao;
    }

    public void setCommentDao(ICommentDao commentDao) {
        this.commentDao = commentDao;
    }
}
