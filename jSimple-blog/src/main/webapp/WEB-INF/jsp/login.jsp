<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/page_tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>用户登录|JSimple-Blog</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="/app/css/pub.css" rel="stylesheet" type="text/css"/>
    <link href="/app/css/login.css" rel="stylesheet" type="text/css"/>
    <!--[if lte IE 9]>
    <script src="/app/js/lib/html5shiv.js"></script><![endif]-->
    <script src="/app/js/lib/jquery.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="body clearfix">
    <div class="boundary">

        <form id="login-form" class="login-form" action="${unencodeUrl}" method="post">
            <fieldset>
                <legend>用户登录</legend>
                <p><label for="userName">用户名</label></p>

                <p><input id="userName" name="name" type="text" value="${name}" size="25"></p>

                <p><label for="password">密　码</label></p>

                <p>
                    <input id="password" name="pwd" type="password" size="25">
                    <label>
                        <c:if test="${isRemember=='1'}">
                            <input type="checkbox" name="isRemember" checked value="1">
                        </c:if>
                        <c:if test="${isRemember!='1'}">
                            <input type="checkbox" name="isRemember" value="1">
                        </c:if>
                        <span class="label-text">记住密码</span></label>
                </p>

                <p><label for="captcha">验证码</label></p>

                <p>
                    <input id="captcha" name="code" type="text" size="4" maxlength="6" autocomplete="off">
                    <span id="captcha-img" class="captcha-img"><img
                            onclick="this.src='/user/validateCode?'+new Date().getTime()"
                            src="/user/validateCode?<%=new Date().getTime()%>" alt="点击更换验证码" class="captcha-img"></span>
                    <input type="submit" value="登 录">

                </p>

                <p>
                    <span style="color: red">${message}</span>
                </p>
            </fieldset>
        </form>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<!--[if lte IE 6]>
<script src="/app/js/lib/ie-upgrade-warning.js"></script>
<![endif]-->
<script type="text/javascript">

</script>
</body>
</html>