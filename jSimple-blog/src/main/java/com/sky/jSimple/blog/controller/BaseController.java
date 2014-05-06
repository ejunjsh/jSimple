package com.sky.jSimple.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.mvc.ControllerBase;

public class BaseController extends ControllerBase {
    public void onException(Throwable e,HttpServletRequest request,HttpServletResponse response)
    {
    	 try {
			errorText(e).ExecuteResult();
		} catch (JSimpleException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
