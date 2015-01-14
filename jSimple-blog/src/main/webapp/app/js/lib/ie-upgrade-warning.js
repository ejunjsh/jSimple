$(function () {

// 检测到此cookie，则不再提示
    if (("; " + document.cookie).indexOf("; noieupgradewarning=") !== -1) {
        return;
    }


// 寻找脚本所在的主机，用于加载样式文件
    var scriptHost, re_scriptName = /\/ie-upgrade-warning.js(?:\?.*?)?$/;
    $('script').each(function (i, script) {
        if (re_scriptName.test(script.src)) {
            scriptHost = script.src.replace(re_scriptName, '') + '/';
            return false;
        }
    });

    if (!scriptHost) {
        return;
    }


// 加载样式文件
    $('<link href="' + scriptHost + 'ie-upgrade-warning.css" rel="stylesheet" type="text/css" />')
        .appendTo($('head'));


    var DIALOGWIDTH = 430, DIALOGHEIGHT = 260;
    var body = $("body"), docElt = document.documentElement;


    var cover = $('<div class="ie-upgrade-warning-cover" />');
// 覆盖全屏并渐显
    cover.css({
        width: docElt.scrollWidth,
        height: docElt.scrollHeight,
        opacity: 0
    }).appendTo(body).animate({
        opacity: 0.8
    }, 300, function () {
        // 覆盖层出现后再显示弹窗
        var pos = computeDialogPosition();
        dialog.animate({
            width: DIALOGWIDTH,
            height: DIALOGHEIGHT,
            left: pos.left,
            top: pos.top
        }, 500);
    });


    var dialog = $('<div class="ie-upgrade-warning">\
	<div class="ie-upgrade-warning-inner">\
		<div class="ie-upgrade-warning-text">\
			<p>您使用的网页浏览器太旧，浏览本站时可能会出现布局错乱等异常情况。</p>\
			<p>如果您使用的是双核浏览器（如搜狗高速浏览器、360极速浏览器、傲游3等），请切换到高速模式，否则，请升级您的浏览器。</p>\
		</div>\
		<p class="ie-upgrade-warning-options">\
			<label><input type="checkbox" class="ie-upgrade-warning-option-nowarning" /> 15分钟内不再提示</label>\
		</p>\
		<p class="ie-upgrade-warning-buttons">\
			<a href="http://windows.microsoft.com/zh-CN/internet-explorer/downloads/ie" target="_blank" class="ie-upgrade-warning-action-upgrade"></a>\
			<a href="#" class="ie-upgrade-warning-action-skip"></a>\
		</p>\
	</div>\
</div>');
    dialog.appendTo(body);

    function computeDialogPosition() {
        return {
            left: parseInt((docElt.clientWidth - DIALOGWIDTH) / 2) + docElt.scrollLeft,
            top: parseInt((docElt.clientHeight - DIALOGHEIGHT) / 2.5) + docElt.scrollTop
        };
    };


    var scrollTimer;
// 滚动的时候改变弹窗位置
    function syncScroll() {
        if (scrollTimer) {
            clearTimeout(scrollTimer);
        }
        scrollTimer = setTimeout(function () {
            var pos = computeDialogPosition();
            dialog.animate(pos);
        }, 100);
    }

    $(window).on("scroll", syncScroll);


// 释放资源
    function disposeIEUpgradeWarning() {
        $(window).off("scroll", syncScroll);
        cover.remove();
        dialog.remove();

        cover = null;
        dialog = null;
    }


    $('a.ie-upgrade-warning-action-skip', dialog).click(function (e) {
        e.preventDefault();
        if ($('input.ie-upgrade-warning-option-nowarning').attr('checked')) {
            var time = new Date();
            time.setMinutes(time.getMinutes() + 15);
            document.cookie = "noieupgradewarning=1; expires=" + time.toUTCString() + "; path=/";
        }
        disposeIEUpgradeWarning();
    });

});