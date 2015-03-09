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
    <link href="/app/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/article-content.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/highline.css" rel="stylesheet" type="text/css"/>
    <script src="/app/js/lib/jquery.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/browserTip.jsp" %>
<div class="container clearfix">
    <div class="boundary container__boundary clearfix">

        <div class="main">
            <div class="article-list">
                <c:forEach items="${blogs.data}" var="blog">
                    <article class="article-list__item">
                        <header class="article-list__item__header clearfix">
                            <h1 class="article-list__item__header__title"><a
                                    href="/blog/${blog.linkName}">${blog.title}</a></h1>

                            <div class="article-list__item__header__meta">
                                <c:if test="${curCate==null}">
                                    <a href="/blog/category/${blog.category.linkName}">${blog.category.name}</a>
                                    &nbsp;&nbsp;|&nbsp;&nbsp;
                                </c:if>

                                    ${blog.relativeDate}
                            </div>
                        </header>
                        <div class="article__content article-list__item__summary">
                                ${blog.shortContent}
                        </div>
                        <footer class="article-list__item__footer clearfix">
                            <div class="article-list__item__footer_info">
                                <em>${blog.viewCount}</em>次阅读，<em>${blog.commentCount}</em>条评论
                            </div>
                            <div class="article-list__item__footer__share clearfix">
                                <a title="${blog.title}" href="http://${host}/blog/${blog.linkName}"
                                   class="share-ico share-ico--weibo" data-sharetype="weibo">&#x349f;</a>
                                <a title="${blog.title}" href="http://${host}/blog/${blog.linkName}"
                                   class="share-ico share-ico--wechat" data-sharetype="wechat">&#xe63c;</a>
                            </div>
                        </footer>
                    </article>
                </c:forEach>
                <nav id="article-list__paginator">
                    <p:pages pageSize="${blogs.pageSize}" pageNo="${blogs.currentPage}"
                             recordCount="${blogs.recordCount}"></p:pages>
                </nav>
            </div>
        </div>

        <%@ include file="/WEB-INF/jsp/common/sidebar.jsp" %>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
