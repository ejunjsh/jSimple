package com.sky.jSimple.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ControllerBase implements IController {

    public void onException(Throwable error, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache");

            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(500);

            writer = response.getWriter();

            writer.write("<h1>jSimple errors</h1>");
            writer.write("<pre>");
            error.printStackTrace(writer);
            writer.write("</pre>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public ActionResult jsp(String path, Object model) {
        return new JspResult(path, model);
    }

    public ActionResult errorText(Throwable e) {
        return new ErrorTextResult(e);
    }

    public ActionResult file(String path) {
        return new FileResult(path);
    }

    public ActionResult freemarker(String path, Object model) {
        return new FreemarkerResult(path, model);
    }

    public ActionResult html(String path) {
        return new htmlResult(path);
    }

    public ActionResult javascript(String jsText) {
        return new JavascriptResult(jsText);
    }

    public ActionResult json(Object model) {
        return new JsonResult(model);
    }

    public ActionResult text(String text) {
        return new TextResult(text);
    }

    public ActionResult velocity(String path, Object model) {
        return new VelocityResult(path, model);
    }

    public ActionResult redirect(String path) {
        return new RedirectResult(path);
    }

//    public Map<String,String> isValid(Object model)
//    {
//       Field[] fields= model.getClass().getFields();
//       for(Field field:fields)
//       {
//    	   Annotation[] annotations= field.getAnnotations();
//    	   for(Annotation annotation:annotations)
//    	   {
//    		   annotation.
//    	   }
//       }
//    }
}
