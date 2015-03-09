<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="sidebar">
    <section class="sidebar__section recommended-articles">
        <h2 class="sidebar__section__title">推荐文章</h2>
        <ol class="sidebar__article-list">
            <c:forEach items="${recommends}" var="blog">
                <li><a href="/blog/${blog.linkName}">${blog.title}</a></li>

            </c:forEach>
        </ol>
    </section>
    <section class="sidebar__section friend-sites">
        <h2 class="sidebar__section__title">友情链接</h2>
        <dl class="sidebar__link-list">
            <dt><a href="http://weibo.com/339238080/" target="_blank">Idiot's Sky</a></dt>
            <dd>作者微博</dd>
            <dt><a href="http://my.oschina.net/idiotsky" target="_blank">oschina's blog</a></dt>
            <dd>作者博客</dd>
            <dt><a href="http://git.oschina.net/sjj050121014" target="_blank">oschina's git</a></dt>
            <dd>作者git</dd>
            <dt><a href="http://heeroluo.net/" target="_blank">heeroluo</a></dt>
            <dd>主题源自</dd>
        </dl>
    </section>
</aside>