package com.sky.jSimple.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface IController {
    void onException(Throwable e,HttpServletRequest request,HttpServletResponse response);
}
