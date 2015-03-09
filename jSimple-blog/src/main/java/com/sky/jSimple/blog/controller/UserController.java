package com.sky.jSimple.blog.controller;

import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.blog.entity.Category;
import com.sky.jSimple.blog.entity.User;
import com.sky.jSimple.blog.result.ValidateCodeResult;
import com.sky.jSimple.blog.service.ICategoryService;
import com.sky.jSimple.blog.service.IUserService;
import com.sky.jSimple.blog.utils.BlogContext;
import com.sky.jSimple.ioc.annotation.Inject;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.Model;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaojunjie on 2015/1/9.
 */
@Bean
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IUserService userService;

    @HttpGet("/user/login")
    public ActionResult loginPage(String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> categories = categoryService.getAllCategories("name", true);
        map.put("categories", categories);

        return jsp("/WEB-INF/jsp/login.jsp", map);
    }

    @HttpPost("/user/login")
    public ActionResult login(Model model) {
        List<Category> categories = categoryService.getAllCategories("name", true);
        model.put("categories", categories);

        String name = model.get("name", String.class);
        //String code = model.get("code", String.class);
        String pwd = model.get("pwd", String.class);
        String path = model.get("path", String.class);
        int isRemember = 1;
        if (StringUtils.isBlank(name) || StringUtils.isBlank(pwd)) {
            model.put("message", "信息没填齐");
            return jsp("/WEB-INF/jsp/login.jsp", model);
        }

//        if (!code.equalsIgnoreCase(RandomValidateCode.getValidateCode(getRequest()))) {
//            model.put("message", "验证码有误");
//            return jsp("/WEB-INF/jsp/login.jsp", model);
//        }


        User user = userService.Login(name, pwd);
        if (user == null) {
            model.put("message", "账号密码有误");
            return jsp("/WEB-INF/jsp/login.jsp", model);
        } else {
            BlogContext.setUser(user, isRemember == 1);
        }

        if (StringUtils.isNotBlank(path)) {
            return redirect(path);
        } else {
            return redirect("/");
        }
    }

    @HttpGet("/user/logout")
    public ActionResult logout(String path) {
        BlogContext.clearUser();
        if (StringUtils.isNotBlank(path)) {
            return redirect(path);
        } else {
            return redirect("/");
        }
    }


    @HttpGet("/user/validateCode")
    public ActionResult validateCode() {
        return new ValidateCodeResult();
    }

    public void setCategoryService(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
