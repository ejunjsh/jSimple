<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/page_tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${blog.title}|JSimple-Blog</title>
    <meta charset="utf-8">
    <meta name="Keywords" content="${blog.tags}">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="/app/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/article-content.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/article-detail.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/highline.css" rel="stylesheet" type="text/css"/>
    <script src="/app/js/lib/jquery.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/browserTip.jsp" %>
<div class="container clearfix">
    <div class="boundary container__boundary clearfix">

        <div class="main">
            <article class="article">
                <header class="article__header">
                    <h1 class="article__header__title">${blog.title}</h1>

                    <div class="article__header__meta clearfix">
                        ${blog.user.nickName}发表于${blog.relativeDate}，已被查看${blog.viewCount}次
                    </div>
                </header>
                <div class="article__content">
                    ${blog.content}
                </div>
                <footer class="article__footer">
                    <div class="article__footer__share">
                        <a title="${blog.title}" href="http://${host}/blog/${blog.linkName}"
                           class="share-btn share-btn--weibo" data-sharetype="weibo"><span class="share-btn__ico">&#x349f;</span>分享到微博</a>
                        <a title="${blog.title}" href="http://${host}/blog/${blog.linkName}"
                           class="share-btn share-btn--wechat" data-sharetype="wechat"><span class="share-btn__ico">&#xe63c;</span>分享到微信</a>
                    </div>

                    <div class="article__footer__adjacents">
                        <ol class="clearfix">


                            <c:if test="${prev!=null}">
                                <li class="article__footer__adjacents__item article__footer__adjacents__item--prev">
                                    <a href="/blog/${prev.linkName}"><span
                                            class="article__footer__adjacents__item__icon"></span>${prev.title}</a>
                                </li>
                            </c:if>
                            <c:if test="${next!=null}">
                                <li class="article__footer__adjacents__item article__footer__adjacents__item--next">
                                    <a href="/blog/${next.linkName}"><span
                                            class="article__footer__adjacents__item__icon"></span>${next.title}</a>
                                </li>
                            </c:if>
                        </ol>
                    </div>

                </footer>
            </article>
            <div class="comment">
                <h2 class="comment__title">评论 (<em class="comment__total">${blog.commentCount}</em>条)</h2>

                <div id="comment__list" class="comment__list">
                    载入中。。。。
                </div>
                <form id="commentForm" action="/comment/post/${blog.id}" method="post"
                      class="comment__form form-layout">
                    <input type="hidden" name="articleid" value="60">

                    <h2 class="comment__title">发表评论</h2>

                    <div class="form-layout__row form-layout__row--3cols clearfix">
                        <c:if test="${curUser==null}">
                        <div class="form-layout__col">
                            <div class="form-item">
                                <p class="form-item__label">
                                    <label for="comment_user_nickname">昵称</label>
                                    <em class="form-item__label__tips form-item__label__tips--required">(必填)</em>
                                </p>
                                <input class="textbox" type="text" id="comment_user_nickname" name="nickName"
                                       maxlength="20">
                            </div>
                        </div>
                        <div class="form-layout__col">
                            <div class="form-item">
                                <p class="form-item__label">
                                    <label for="comment_user_email">Email</label>
                                    <em class="form-item__label__tips">(选填，不公开)</em>
                                </p>
                                <input class="textbox" type="text" id="comment_user_email" name="email" maxlength="60">
                            </div>
                        </div>
                        <div class="form-layout__col">
                            <div class="form-item">
                                <p class="form-item__label">
                                    <label for="comment_user_qq">QQ</label>
                                    <em class="form-item__label__tips">(选填，不公开)</em>
                                </p>
                                <input class="textbox" type="text" id="comment_user_qq" name="website" maxlength="15">
                            </div>
                        </div>
                    </div>
                    </c:if>
                    <div class="form-layout__row">
                        <div class="form-item">
                            <p class="form-item__label">
                                <label for="comment_content">内容</label>
                                <em class="form-item__label__tips form-item__label__tips--required">(必填)</em>
                            </p>
                            <textarea class="textbox" id="comment_content" name="content" cols="50"
                                      rows="10"></textarea>
                        </div>
                    </div>
                    <div class="form-layout__row">
                        <div class="form-item">
                            <input id="ajaxSubmitBtn" type="button" value="发 表" class="btn"
                                   data-submitingtext="发表中，请稍后">
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script type="text/javascript">
    $(function () {
        loadComments("/comments/${blog.id}");
        $("#ajaxSubmitBtn").click(addComment);
    });

    var loadComments = function (url) {
        $.ajax({url: url, cache: false, success: function (data) {
            $("#comment__list").html(data);
            $("#comment__list").find("#comment-list__paginator a").click(function () {
                loadComments(this.href);

                return false;
            });
        }});
    }

    var addComment = function () {
        $.ajax({url: $("#commentForm").attr("action"), type: "POST", data: $("#commentForm").serialize(), success: function (data) {
            alert(data);
            loadComments("/comments/${blog.id}");
        }, error: function (error) {
            alert(error.responseText);
        }});
    }
</script>
</body>
</html>