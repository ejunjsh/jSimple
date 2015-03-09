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
    <link href="/app/css/login.css" rel="stylesheet" type="text/css"/>
    <!--[if lte IE 9]>
    <script src="/app/js/lib/html5shiv.js"></script><![endif]-->
    <script src="/app/js/lib/jquery.js"></script>
</head>

<body>

<div class="container">
    <form action="${unencodeUrl}" method="post" class="login-form" id="login-form">
        <input type="text" name="name" class="textbox" placeholder="用户名" maxlength="20" autofocus="autofocus"/>&nbsp;
        <input type="password" name="pwd" class="textbox" placeholder="密码" maxlength="16"/>
        <input type="submit" class="button" value="登 录"/>
    </form>
    <script type="text/javascript">
        <c:if test="${message!=null&&message!=''}">
        alert("${message}");
        </c:if>
    </script>
</div>
</body>
</html>