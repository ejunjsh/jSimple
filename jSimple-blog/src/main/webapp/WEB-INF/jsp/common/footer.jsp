<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer id="footer" class="footer">
    <div class="boundary clearfix">
        <nav>
            当前位置：
            <span id="currentPosition" class="current-position"><c:if test="${curCate==null}">首页</c:if><c:if
                    test="${curCate!=null}">${curCate.name}</c:if></span>
            <ul id="expressNav" class="express-nav">
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
        <div class="copyright">Copyright &copy; 2014-2015 <a href="http://weibo.com/339238080" target="_blank">Idiot's
            Sky</a>,Theme By <a href="http://heeroluo.net" target="_blank">Heero&#39;s Blog</a></div>
    </div>
</footer>
<script type="text/javascript">
    $(function () {
        var hideNav = function () {
            $("#expressNav").fadeOut();
            $(document).unbind("click", hideNav);
            $("#currentPosition").removeClass("expanded");
        };
        $("#currentPosition").click(function (e) {
            if (!$("#expressNav").is(":visible")) {
                $("#expressNav").fadeIn();
                $(document).bind("click", hideNav);
                e.stopPropagation();
                $(this).addClass("expanded");
            }
        });
    });
</script>