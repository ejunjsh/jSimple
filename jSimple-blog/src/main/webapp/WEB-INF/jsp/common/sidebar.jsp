<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="sidebar">
    <section>
        <h2 class="titlebar">推荐文章</h2>
        <ol class="sidebar-articlelist">
            <li><a href="/Article/Detail/111" target="_blank">了解模块化开发</a></li>
            <li><a href="/Article/Detail/102" target="_blank">Javascript Module Loader实现原理</a></li>
            <li><a href="/Article/Detail/107" target="_blank">写在《高达SEED重制版》即将结束之际</a></li>
            <li><a href="/Article/Detail/67" target="_blank">jQuery.animate简单分析</a></li>
            <li><a href="/Article/Detail/73" target="_blank">前端Javascript模板引擎</a></li>
            <li><a href="/Article/Detail/95" target="_blank">使用canvas绘制时钟</a></li>
            <li><a href="/Article/Detail/58" target="_blank">输入法也做代码库</a></li>
        </ol>
    </section>

    <section class="sidebar-articlerank">
        <h2 class="titlebar">阅读排行</h2>
        <ol class="sidebar-articlelist">
            <c:forEach items="${topViewCountBlogs}" var="blog">
                <li class="clearfix">
                    <div class="sidebar-articlerank-viewcount">${blog.viewCount}</div>
                    <div class="sidebar-articlerank-title"><a href="/blog/${blog.linkName}">${blog.title}</a></div>
                </li>
            </c:forEach>
        </ol>
    </section>
    <section class="sidebar-weibo">
        <h2 class="titlebar">我的微博</h2>

        <div class="sidebar-weibo-inner">
            <iframe class="share_self" frameborder="0" scrolling="no"
                    src="http://widget.weibo.com/weiboshow/index.php?width=0&amp;height=450&amp;fansRow=2&amp;ptype=1&amp;speed=0&amp;skin=1&amp;isTitle=0&amp;noborder=1&amp;isWeibo=1&amp;isFans=0&amp;uid=1647840843&amp;verifier=6b8ce55c&amp;dpc=1"></iframe>
        </div>
    </section>
    <section class="sidebar-others">
        <h2 class="titlebar">其他功能</h2>
        <ul class="clearfix">
            <li class="message"><a href="/Article/Detail/99">给我留言</a></li>
            <li class="rss"><a href="/Article/RSS" target="_blank">订阅 RSS</a></li>
            <li class="login"><a href="/user/login?path=${encodeUrl}">用户登录</a></li>
        </ul>
    </section>
    <section class="sidebar-links">
        <h2 class="titlebar">友情链接</h2>
        <dl class="clearfix">
            <dt><a href="http://jraiser.org/" target="_blank">JRaiser Project</a></dt>
            <dd>JRaiser模块化JS类库</dd>
            <dt><a href="http://jooben.blog.51cto.com/" target="_blank">leolee&#39;s blog</a></dt>
            <dd>leolee&#39;s blog</dd>
            <dt><a href="http://www.bokeyy.com/" target="_blank">博客歪歪</a></dt>
            <dd>袁源（歪歪）的个人博客</dd>
            <dt><a href="http://www.zhoumingzhi.com/" target="_blank">记事本</a></dt>
            <dd>这里全是流水账</dd>
            <dt><a href="http://terran.cc/" target="_blank">Terran&#39;s Blog</a></dt>
            <dd>产品设计、交互设计、Web前端开发、生活思考</dd>
            <dt><a href="http://www.liuxiaofan.com" target="_blank">刘晓帆的博客</a></dt>
            <dd>专注互联网前端技术、UI设计、用户体验</dd>
            <dt><a href="http://http://www.crayhuang.info/" target="_blank">Cray&#39;s Blog</a></dt>
            <dd>Enjoy work, enjoy life...</dd>
        </dl>
    </section>
</aside>