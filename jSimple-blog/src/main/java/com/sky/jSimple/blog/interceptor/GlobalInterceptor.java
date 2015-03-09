package com.sky.jSimple.blog.interceptor;

import com.sky.jSimple.blog.utils.BlogContext;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Interceptor;
import com.sky.jSimple.mvc.annotation.Order;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

@Order(99)
public class GlobalInterceptor extends Interceptor {

    @Override
    public boolean getGlobal() {
        return true;
    }

    @Override
    public ActionResult before(Class<?> cls, Method method, Object[] params) {
        //保存原路径
        request.setAttribute("originalUrl", request.getRequestURI());
        String encodeUrl;
        if (StringUtils.isNotBlank(request.getQueryString())) {
            encodeUrl = request.getRequestURI() + "?" + request.getQueryString();
        } else {
            encodeUrl = request.getRequestURI();
        }

        try {
            request.setAttribute("unencodeUrl", encodeUrl);
            encodeUrl = URLEncoder.encode(encodeUrl, "UTF-8");
            request.setAttribute("encodeUrl", encodeUrl);

            request.setAttribute("host", request.getServerName() + ":" + request.getServerPort());
        } catch (UnsupportedEncodingException e) {
            throw new JSimpleException(e);
        }

        //保存当前user
        request.setAttribute("curUser", BlogContext.getUser());
        return null;
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, ActionResult result) {

    }

}
