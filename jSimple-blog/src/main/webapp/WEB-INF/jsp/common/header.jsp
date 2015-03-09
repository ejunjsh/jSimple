<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="header" class="header">
    <div class="boundary header_boundary clearfix">
        <div class="header__logo"><a href="/">jSimple's Blog</a></div>
        <nav class="header__nav">
            <span class="header__nav__toggle"></span>
            <ul class="header__nav__list clearfix">
                <c:if test="${curCate==null}">
                    <li class="header__nav__list-item header__nav__list-item--current"><a href="/">首页</a></li>
                </c:if>
                <c:if test="${curCate!=null}">
                    <li class="header__nav__list-item"><a href="/">首页</a></li>
                </c:if>
                <c:forEach items="${categories}" var="category">
                    <c:if test="${curCate==null||curCate.name!=category.name}">
                        <li class="header__nav__list-item">
                        <a href="/blog/category/${category.linkName}">${category.name}</a>
                        </li>
                    </c:if>
                    <c:if test="${curCate.name==category.name}">
                        <li class="header__nav__list-item header__nav__list-item--current">
                        <a href="/blog/category/${category.linkName}">${category.name}</a>
                        </li>
                    </c:if>

                </c:forEach>

            </ul>
        </nav>
        <div class="header__user-panel">
            <c:if test="${curUser!=null}">
                <div class="header__user-panel__login">欢迎${curUser.nickName}，<a href="/admin">后台</a>，<a
                        href="/user/logout?path=${encodeUrl}">退出</a></div>
            </c:if>
            <c:if test="${curUser==null}">
                <div class="header__user-panel__login"><a href="/user/login?path=${encodeUrl}">登录</a></div>
            </c:if>
        </div>
    </div>
</header>
<script type="text/javascript">
    $(".header__nav__toggle").click(function (e) {
        $(this).addClass("header__nav__toggle--on").next().addClass("header__nav__list--on");
        $(document).bind("click", function () {
            $(".header__nav__toggle").removeClass("header__nav__toggle--on").next().removeClass("header__nav__list--on");
            $(document).unbind("click");
        });
        e.stopPropagation();
    });

</script>