package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Comment;
import com.sky.jSimple.blog.model.Pagination;
import com.sky.jSimple.blog.service.ICommentService;
import com.sky.jSimple.blog.utils.BlogContext;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Model;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by shaojunjie on 2015/2/16.
 */
@Bean
public class CommentController extends AjaxController {

    @Inject
    private ICommentService commentService;

    @HttpPost("/comment/post/{blogId}/?")
    public ActionResult postComment(Comment comment, String code) {


        if (StringUtils.isBlank(comment.getContent())) {
            throw new JSimpleException("请输入评论内容");
        }

        if (BlogContext.getUser() == null && StringUtils.isBlank(comment.getNickName())) {
            throw new JSimpleException("请输入称呼");
        }

//        if(code==null||!code.equalsIgnoreCase(RandomValidateCode.getValidateCode(WebContext.getRequest())))
//        {
//            throw new JSimpleException("请输入正确验证码");
//        }

        comment.setCreatedDate(new Date());
        comment.setLastModifiedDate(new Date());
        if (BlogContext.getUser() != null) {
            comment.setUid(BlogContext.getUser().getId());
        }
        commentService.insert(comment);

        return text("评论成功");
    }

    @HttpGet("/comments/{blogId}/?")
    public ActionResult getComments(long blogId, int page) {
        if (page == 0) {
            page = 1;
        }

        Pagination<Comment> comments = commentService.getByBlogId(page, Pagination.PAGESIZE, blogId, "createdDate", false);
        Model model = new Model();
        model.put("comments", comments);
        return freemarker("/WEB-INF/ftl/comments.ftl", model);
    }


    public void setCommentService(ICommentService commentService) {
        this.commentService = commentService;
    }
}
