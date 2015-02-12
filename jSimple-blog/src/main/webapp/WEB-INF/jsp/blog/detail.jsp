<%@ page import="java.util.Date" %>
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

                        <!-- JiaThis Button BEGIN -->
                        <div class="jiathis_style_32x32" style="float:right;">
                            <a class="jiathis_button_qzone"></a>
                            <a class="jiathis_button_tsina"></a>
                            <a class="jiathis_button_tqq"></a>
                            <a class="jiathis_button_weixin"></a>
                            <a class="jiathis_button_renren"></a>
                            <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis"
                               target="_blank"></a>
                            <a class="jiathis_counter_style"></a>
                        </div>
                        <script type="text/javascript" src="http://v3.jiathis.com/code_mini/jia.js"
                                charset="utf-8"></script>
                        <!-- JiaThis Button END -->

                        <div style="clear: both"></div>
                    </footer>
                </article>
                <section id="comment-list" class="comment-list">
                    <h2 class="titlebar">评论（共<em class="comment-count">0</em>条）</h2>

                    <div><p class="comment-tips">暂无评论</p></div>
                </section>
                <div id="comment-form" class="comment-form">
                    <h2 class="titlebar">参与讨论</h2>

                    <form action="/Comment/Post/117" method="post" class="clearfix" id="539412456">
                        <div class="col-l">
                            <p class="form-field textbox-with-label"><textarea textholder="true" class="form-text"
                                                                               name="Content" id="Content" rows="5"
                                                                               cols="40"
                                                                               style="height: 125px;"></textarea><label
                                    for="Content" style="display: block;">请填写评论内容 <span
                                    class="required">*</span></label></p>

                            <p class="form-prompt">(带 <span class="required">*</span> 的是必填项)</p>
                        </div>
                        <div class="col-r">
                            <p class="form-field textbox-with-label"><input textholder="true" type="text"
                                                                            name="AuthorName" id="AuthorName"
                                                                            class="form-text" maxlength="20"><label
                                    for="AuthorName" style="display: block;">您的称呼 <span
                                    class="required">*</span></label></p>

                            <p class="form-field textbox-with-label"><input textholder="true" type="text" name="Email"
                                                                            id="Email" class="form-text"><label
                                    for="Email" style="display: block;">Email（选填，保密）</label></p>

                            <p class="form-field textbox-with-label"><input textholder="true" type="text"
                                                                            name="HomePage" id="HomePage"
                                                                            class="form-text"><label for="HomePage"
                                                                                                     style="display: block;">个人博客或主页（选填）</label>
                            </p>

                            <div class="captcha">
						<span class="form-field textbox-with-label">
							<input textholder="true" type="text" name="captcha" id="captcha" class="form-text"
                                   maxlength="4"><label for="captcha" style="display: block;">验证码 <span
                                class="required">*</span></label>
						</span>
                                <span class="captcha-img"><img
                                        onclick="this.src='/user/validateCode?'+new Date().getTime()"
                                        src="/user/validateCode?<%=new Date().getTime()%>" alt="点击更换验证码" alt="点击更换验证码"
                                        class="captcha-img"></span>
                            </div>
                            <p class="form-button"><input type="submit" value="发 表" class="button"></p>
                        </div>
                        <input type="hidden" name="ArticleId" value="117">
                        <input type="hidden" id="comment-minlength" value="10">
                        <input type="hidden" id="comment-maxlength" value="500">
                    </form>
                </div>
                <div class="adjacent-articles">
                    <p>上一篇：<a href="/Article/Detail/115">从千分位格式化谈JS性能优化</a></p>
                </div>
            </div>
        </div>

        <%@ include file="/WEB-INF/jsp/common/sidebar.jsp" %>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<!--[if lte IE 6]>
<script src="/app/js/lib/ie-upgrade-warning.js"></script>
<![endif]-->
<script type="text/javascript">
    $(function () {
        $("input[textholder='true']").focus(function () {
            $(this).next().fadeOut(200);
        }).blur(function () {
            if (!this.value) {
                $(this).next().fadeIn(200);
            }
        });
        $("textarea[textholder='true']").focus(function () {
            $(this).next().fadeOut(200);
        }).blur(function () {
            if (!this.value) {
                $(this).next().fadeIn(200);
            }
        });
    });
</script>
</body>
</html>