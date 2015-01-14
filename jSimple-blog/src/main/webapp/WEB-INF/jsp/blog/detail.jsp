<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/page_tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${blog.title}|JSimple-Blog</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="/app/css/pub.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/blog-detail.css" rel="stylesheet" type="text/css"/>
    <!--[if lte IE 9]>
    <script src="/app/js/lib/html5shiv.js"></script><![endif]-->
    <script src="/app/js/lib/jquery.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="body clearfix">
    <div class="boundary">

        <div class="main">
            <div class="inner">
                <article class="article">
                    <header>
                        <h1 class="article-title">${blog.title}</h1>
                        <ul class="article-info clearfix">
                            <li class="article-posttime">
                                <address>${blog.user.nickName}</address>
                                发表于
                                <time datetime="{{blog.createdDateF}}">${blog.createdDateF}</time>
                            </li>
                            <li>分类：<a href="/blog/category/${blog.category.linkName}">${blog.category.name}</a></li>
                            <li>浏览次数：${blog.viewCount}</li>
                            <li>评论次数：<span class="comment-count">3</span></li>
                        </ul>
                    </header>
                    <div class="article-content">
                        ${blog.content}
                    </div>
                    <footer>

                    </footer>
                </article>
            </div>
        </div>

        <%@ include file="/WEB-INF/jsp/common/sidebar.jsp" %>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<!--[if lte IE 6]>
<script src="/app/js/lib/ie-upgrade-warning.js"></script>
<![endif]-->
</body>
</html>