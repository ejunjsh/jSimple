package com.sky.jSimple.blog.controller;

import com.sky.jSimple.exception.JSimpleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shaojunjie on 2015/2/17.
 */
public class AjaxController extends BaseController {
    public void onException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        try {
            errorText(e).ExecuteResult();
        } catch (JSimpleException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
