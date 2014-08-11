package com.sky.jSimple.blog.controller;


import com.sky.jSimple.Annotation.Bean;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.mvc.ActionResult;
import com.sky.jSimple.mvc.WebContext;
import com.sky.jSimple.mvc.annotation.HttpPost;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Bean
public class FileUploadController extends BaseController {

    private final int FILE_SIZE = 16 * 1024;

    @HttpPost("/api/upload/?")
    public ActionResult upload(File imgFile) {
        String path = "/upload/" + UUID.randomUUID() + "." + imgFile.getName().split("\\.")[1];
        String realPath = WebContext.getServletContext().getRealPath(path);
        File savedFile = new File(realPath);
        upLoadFile(imgFile, savedFile);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", 0);
        map.put("url", path);
        return json(map);
    }

    public void onException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", 1);
            map.put("message", e.getMessage());
            json(map).ExecuteResult();
        } catch (JSimpleException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public boolean upLoadFile(File source, File target) {
        boolean isSuccess = false;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(source), FILE_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(target),
                    FILE_SIZE);
            byte[] buffer = new byte[FILE_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
                isSuccess = true;
            }
        } catch (IOException ex) {
            isSuccess = false;
            ex.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
            }
        }
        return isSuccess;
    }

}
