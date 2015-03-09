<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer class="footer">
    <div class="boundary footer__boundary clearfix">
        <div class="footer__col-left">
            <nav class="footer__nav">
                <ul class="clearfix">
                    <li><a href="/">首页</a></li>
                    <c:forEach items="${categories}" var="category">
                        <li>
                            <a href="/blog/category/${category.linkName}">${category.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
            <p>本站作品采用 <a href="http://creativecommons.org/licenses/by-nc/4.0/" target="_blank">知识共享署名-非商业性使用 4.0
                国际许可协议</a> 进行许可。</p>

            <p>Copyright © 2009-2015 <a href="/">jSimple's Blog</a>. All rights reserved.</p>
        </div>
        <div class="footer__col-right">
            <p>Power by
                <a href="http://git.oschina.net/sjj050121014/jSimple" target="_blank">jSimple</a></p>
        </div>
    </div>
</footer>
<script src="/app/js/lib/jquery.qrcode.min.js"></script>
<script src="/app/js/lib/highline.js"></script>
<script type="text/javascript">
    $("a[data-sharetype='weibo']").click(function () {
        var url = "http://service.weibo.com/share/share.php?";
        url += "title=";
        url += encodeURI(this.title);
        url += "&url=";
        url += encodeURI(this.href);
        window.open(url);
        return false;
    });
    $("a[data-sharetype='wechat']").click(function () {
        var overlayer = $('<div class="overlayer" style="top: 0px; left: 0px; position: fixed; display: block; z-index: 10000; width: 100%; height: 100%; background-color: black; opacity: 0.7;"></div>');
        var qrCodelayer = $('<div class="share-wechat"><p>扫一扫，分享到微信</p><p class="share-wechat__qrcode" "><div id="qrContainer"></div></p><p class="share-wechat__close">点击任意位置关闭</p></div>')
        overlayer.appendTo($("body"));
        qrCodelayer.appendTo($("body"));
        qrCodelayer.find("#qrContainer").qrcode({
            width: 128, //宽度
            height: 128, //高度
            text: encodeURI(this.href)
        });
        $(document).bind("click", function () {
            qrCodelayer.remove();
            overlayer.remove();
            $(document).unbind("click");
        });
        return false;
    });

    $("pre").each(function (i) {
        var code = $(this).text();
        var codeType = this.className.split(":")[1].replace(";", "");
        code = "<code class='" + codeType + "'>" + code + "</code>";
        $(this).html(code);
    });

    hljs.configure({
        tabReplace: '    '
    });
    hljs.initHighlightingOnLoad();
</script>