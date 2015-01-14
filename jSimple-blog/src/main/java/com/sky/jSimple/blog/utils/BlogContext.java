package com.sky.jSimple.blog.utils;

import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.blog.entity.User;
import com.sky.jSimple.blog.service.IUserService;
import com.sky.jSimple.blog.service.UserService;
import com.sky.jSimple.mvc.WebContext;
import org.apache.commons.lang.StringUtils;

/**
 * Created by shaojunjie on 2015/1/13.
 */
public class BlogContext {

    private static final String USERKEY = "USERKEY";
    private static final String LOGINUSERID = "LOGINUSERID";

    private static IUserService getUserService() {
        return BeanContainer.getBean(UserService.class);
    }

    /**
     * 返回当前用户
     *
     * @return
     */
    public static User getUser() {
        Object o = WebContext.getRequest().getSession().getAttribute(USERKEY);
        if (o == null) {
            String cookiesValue = WebContext.Cookie.get(LOGINUSERID);
            if (StringUtils.isNotBlank(cookiesValue)) {
                User user = getUserService().getById(Long.parseLong(cookiesValue));
                if (user != null) {
                    WebContext.getRequest().getSession().setAttribute(USERKEY, user);
                    return user;
                }
            }
            return null;
        } else {
            return (User) o;
        }
    }

    /**
     * 设置当前用户
     *
     * @param user
     * @param isRemember
     */
    public static void setUser(User user, boolean isRemember) {
        WebContext.getRequest().getSession().setAttribute(USERKEY, user);

        if (isRemember) {
            WebContext.Cookie.put(LOGINUSERID, user.getId(), 7 * 24 * 60 * 60);//一个星期
        }
    }

    /**
     * 清除用户（退出登录）
     */
    public static void clearUser() {
        WebContext.Session.remove(USERKEY);
        WebContext.Cookie.remove(LOGINUSERID);
    }

}
