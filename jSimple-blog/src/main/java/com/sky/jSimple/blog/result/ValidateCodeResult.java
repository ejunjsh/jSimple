package com.sky.jSimple.blog.result;

import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.utils.RandomValidateCode;

/**
 * Created by shaojunjie on 2015/1/9.
 */
public class ValidateCodeResult extends ActionResult {
    @Override
    public void ExecuteResult() {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            throw new JSimpleException(e);
        }
    }

}
