package com.sky.jSimple.blog.controller;


import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;

@Bean
public class TestController extends BaseController {


    @HttpGet("/api/test")
    public ActionResult index() {
        return html("/test/index.html");
    }

    @HttpPost("/api/test/formTest")
    public ActionResult formTest(int urlInteger, int formInteger) {
        return html("/test/index.html");
    }
}
