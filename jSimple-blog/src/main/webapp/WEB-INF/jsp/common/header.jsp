<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="header" class="header">
    <div class="boundary">
        <h1 class="logo"><a href="/">JSimple-Blog</a></h1>
        <nav class="global-nav">
            <ul>
                <c:if test="${curCate==null}">
                    <li class="current"><a href="/">首页</a></li>
                </c:if>
                <c:if test="${curCate!=null}">
                    <li><a href="/">首页</a></li>
                </c:if>
                <c:forEach items="${categories}" var="category">
                    <c:if test="${curCate==null||curCate.name!=category.name}">
                        <li>
                            <a href="/blog/category/${category.linkName}">${category.name}</a>
                        </li>
                    </c:if>
                    <c:if test="${curCate.name==category.name}">
                        <li class="current">
                            <a href="/blog/category/${category.linkName}">${category.name}</a>
                        </li>
                    </c:if>

                </c:forEach>
            </ul>
        </nav>
    </div>
</header>