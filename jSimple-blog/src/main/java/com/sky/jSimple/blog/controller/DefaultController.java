package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.annotation.Login;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.HttpGet;

/**
 * 配置默认转发到哪个页面
 */
@Bean
public class DefaultController extends BaseController {

    /**
     * admin开头都跳到这个页面下
     *
     * @return
     */
    @Login
    @HttpGet("^/admin.*")
    public ActionResult admin() {
        return html("/app/admin.html");
    }


}
