<#assign c=JspTaglibs["/WEB-INF/pageTag.tld"] />

<#if  comments.recordCount=0>
<p class="comment__list__tips">暂无评论</p>
</#if>
<#if comments.recordCount!=0>
    <#list comments.data as comment>
        <#if comment.uid!=0>
        <article class="comment__list__item comment__list__item--isuser">
            <header class="comment__list__item__header clearfix">
                <div class="comment__list__item__header__author"
                "><em>${comment.user.nickName}</em> 说：</div>
                <div class="comment__list__item__header__pubtime">发表于${comment.relativeDate}</div>
            </header>
            <div class="comment__list__item__content">${comment.content}</div>
        </article>
        </#if>
        <#if comment.uid=0>
        <article class="comment__list__item">
            <header class="comment__list__item__header clearfix">
                <div class="comment__list__item__header__author"
                "><em>${comment.nickName}</em> 说：</div>
                <div class="comment__list__item__header__pubtime">发表于${comment.relativeDate}</div>
            </header>
            <div class="comment__list__item__content">${comment.content}</div>
        </article>
        </#if>

    </#list>
</#if>
<nav id="comment-list__paginator">
<@c.pages pageSize="${comments.pageSize}" pageNo="${comments.currentPage}"
recordCount="${comments.recordCount}"></@c.pages>

</nav>


