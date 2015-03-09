package com.sky.jSimple.blog.tag;

import com.sky.jSimple.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by shaojunjie on 2014/12/29.
 */
public class PageTag extends TagSupport {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int pageNo;
    private int recordCount;
    private int pageSize;
    private String url;
    private String anchor;

    @Override
    public int doStartTag() throws JspException {
        // TODO Auto-generated method stub

        int pageCount = (this.recordCount + this.pageSize - 1) / this.pageSize;
        StringBuilder sb = new StringBuilder();

        if (this.recordCount != 0 && this.recordCount > this.pageSize) {
            sb.append("<ol class=\"paginator\">");
            if (this.pageNo > pageCount) {
                this.pageNo = pageCount;
            }
            if (this.pageNo < 1) {
                this.pageNo = 1;
            }
            HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

            url = request.getAttribute("originalUrl").toString();
            url += "?";
            if (StringUtil.isNotEmpty(request.getQueryString())) {
                String queryString = request.getQueryString();
                url += queryString.replaceAll("page=[0-9]*", "");
                if (!url.endsWith("&") && !url.endsWith("?")) {
                    url += "&";
                }
            }

            if (this.pageNo > 1) {
                sb.append("<li class=\"paginator__item paginator__item--prev\"><a class=\"paginator__item__text\" href='" + this.url + "page=" + (this.pageNo - 1) + "'><span class=\"paginator__item__text__icon\"></span></a></li>");
            }
            int start = 1;
            if (this.pageNo > 4) {
                start = this.pageNo - 1;
                sb.append("<li class=\"paginator__item paginator__item--number\"><a class=\"paginator__item__text\" href='" + this.url + "page=1'>1</a></li>");
                sb.append("<li class=\"paginator__item paginator__item--number\"><a class=\"paginator__item__text\" href='" + this.url + "page=2'>2</a></li>");
                sb.append("<li  class=\"paginator__item paginator__item--ellipsis\"><span class=\"paginator__item__text\"><span class=\"paginator__item__text__icon\"></span></span></li>");
            }
            int end = this.pageNo + 1;
            if (end > pageCount) {
                end = pageCount;
            }
            for (int i = start; i <= end; i++) {
                if (this.pageNo == i) {
                    sb.append("<li class=\"paginator__item paginator__item--number paginator__item--current\"><span  class=\"paginator__item__text\">" + i + "</span></li>");
                } else {
                    sb.append("<li class=\"paginator__item paginator__item--number\"><a  class=\"paginator__item__text\" href='" + this.url + "page=" + i + "'>").append(i).append("</a></li>");
                }
            }
            if (end < pageCount - 2) {
                sb.append("<li  class=\"paginator__item paginator__item--ellipsis\"><span  class=\"paginator__item__text\"><span class=\"paginator__item__text__icon\"></span></span></li>");
            }
            if (end < pageCount - 1) {
                sb.append("<li   class=\"paginator__item paginator__item--number\"><a class=\"paginator__item__text\" href='" + this.url + "page=" + (pageCount - 1) + "'>").append((pageCount - 1) + "</a></li>");
            }
            if (end < pageCount) {
                sb.append("<li class=\"paginator__item paginator__item--number\"><a  class=\"paginator__item__text\" href='" + this.url + "page=" + pageCount + "'>").append(pageCount + "</a></li>");
            }
            if (this.pageNo != pageCount) {
                sb.append("<li class=\"paginator__item paginator__item--next\"><a  class=\"paginator__item__text\" href='" + this.url + "page=" + (this.pageNo + 1) + "'><span class=\"paginator__item__text__icon\"></span></a></li>");
            }
        }
        if (anchor != null) {
            sb.append("<script type='text/javascript'>$('.paginator a').each(function(){var href=$(this).attr('href');$(this).attr('href',href+'" + anchor + "')});</script>");
        }
        sb.append("</ol>");
        try {
            this.pageContext.getOut().println(sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return 0;
    }


    public void setPageNo(String pageNo) {
        this.pageNo = Integer.parseInt(pageNo);
    }


    public void setRecordCount(String recordCount) {
        this.recordCount = Integer.parseInt(recordCount);
    }

    public void setPageSize(String pageSize) {
        this.pageSize = Integer.parseInt(pageSize);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAnchor(String anchor) {
        this.anchor = "#" + anchor;
    }

}
