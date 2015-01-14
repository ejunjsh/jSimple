<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/page_tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:if test="${curCate==null}">
        <title>首页|JSimple-Blog</title>
    </c:if>
    <c:if test="${curCate!=null}">
        <title>${curCate.name}|JSimple-Blog</title>
    </c:if>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="/app/css/pub.css" rel="stylesheet" type="text/css"/>
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
                <c:forEach items="${blogs.data}" var="blog">
                    <article class="article">
                        <header>
                            <time datetime="${blog.createdDate}" class="article-postdate"><span
                                    class="day">${blog.createdDay}</span>${blog.createdMonth}月
                            </time>
                            <h2 class="article-title"><a href="/blog/${blog.linkName}">${blog.title}</a></h2>
                            <ul class="clearfix">
                                <li>分类：<a href="/blog/category/${blog.category.linkName}">${blog.category.name}</a></li>
                                <li>浏览次数：<a href="/blog/${blog.linkName}">${blog.viewCount}</a></li>
                                <li>评论次数：<a href="/blog/${blog.linkName}">0</a></li>
                            </ul>
                        </header>
                        <div class="article-content">
                                ${blog.shortContent}
                        </div>
                        <footer>
                            <ul class="clearfix">
                                <li class="article-posttime">
                                    <address>${blog.user.nickName}</address>
                                    发表于
                                    <time datetime="{{blog.createdDateF}}">${blog.createdDateF}</time>
                                </li>
                                <li class="article-entry"><a href="/blog/${blog.linkName}">阅读全文 &raquo;</a></li>
                            </ul>
                        </footer>
                    </article>
                </c:forEach>
                <p:pages pageSize="${blogs.pageSize}" pageNo="${blogs.currentPage}"
                         recordCount="${blogs.recordCount}"></p:pages>
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