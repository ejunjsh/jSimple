package com.sky.jSimple.blog.controller;

import com.sky.jSimple.mvc.ControllerBase;
import com.sky.jSimple.mvc.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController extends ControllerBase {
//    public void onException(Throwable e,HttpServletRequest request,HttpServletResponse response)
//    {
//    	 try {
//			errorText(e).ExecuteResult();
//		} catch (JSimpleException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    }


    public HttpServletRequest getRequest()
    {
        return WebContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return WebContext.getResponse();
    }
}
